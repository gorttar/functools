package concurrent.port;

import data.Pair;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * port implementation based on {@link BlockingQueue} with wrapping messages to {@link Optional}
 * and {@link java.util.Optional#empty()} as port close signal
 *
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-20 18:27)
 */
public class OptionalBufferedPort<A> implements Port<A> {
    private boolean closed = false;
    private final Lock portLock = new ReentrantLock();
    private final BlockingQueue<Optional<A>> queue;
    private final PortItr portItr;

    private OptionalBufferedPort(int bufferSize) {
        queue = new ArrayBlockingQueue<>(bufferSize);
        portItr = new PortItr();
    }

    @Override
    public void send(@Nonnull A message) throws InterruptedException, IllegalStateException {
        portLock.lockInterruptibly();
        try {
            if (closed) {
                throw new IllegalStateException("Port is closed");
            }
            queue.put(Optional.of(message));
        } catch (Exception e) {
            // if we are receiving any exception then we should cleanup queue and close port
            purge();
            throw e;
        } finally {
            portLock.unlock();
        }
    }

    private void purge() {
        queue.clear();
        try {
            queue.put(Optional.empty());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        closed = true;
    }

    @Override
    public Response sendIfOpen(@Nonnull A message) throws InterruptedException {
        try {
            portLock.lockInterruptibly();
            if (!closed) {
                queue.put(Optional.of(message));
            }
        } catch (Exception e) {
            // if we are receiving any exception then we should cleanup queue and close port
            purge();
            throw e;
        } finally {
            portLock.unlock();
        }
        return closed ? Response.CLOSED : Response.OK;
    }

    @Override
    public int remainingCapacity() {
        return queue.remainingCapacity();
    }

    @Override
    public Response sendImmediate(@Nonnull A message) throws IllegalStateException {
        if (closed) {
            throw new IllegalStateException("Port is closed");
        }
        return queue.offer(Optional.of(message)) ? Response.OK : Response.UNABLE_TO_RECEIVE_NOW;
    }

    @Override
    public Response sendImmediateIfOpen(@Nonnull A message) {
        portLock.lock();
        final Response result;
        if (closed) {
            result = Response.CLOSED;
        } else {
            result = sendImmediate(message);
        }
        portLock.unlock();
        return result;
    }

    @Override
    public void close() throws IOException {
        portLock.lock();
        try {
            if (!closed) {
                closed = true;
                queue.put(Optional.empty());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            portLock.unlock();
        }
    }

    public static <T> Pair<Port<T>, Stream<T>> createPortWithStream(int bufferSize) {
        final OptionalBufferedPort<T> port = new OptionalBufferedPort<>(bufferSize);
        final Iterator<Optional<T>> portItr = port.portItr;
        return Pair.tup(
                port,
                StreamSupport.stream(
                        Spliterators.spliterator(new Iterator<T>() {
                            @Override
                            public boolean hasNext() {
                                return portItr.hasNext();
                            }

                            @Override
                            public T next() {
                                return portItr.next().orElseThrow(() -> new NoSuchElementException("No value present"));
                            }
                        }, -1, Spliterator.IMMUTABLE),
                        false));
    }

    private class PortItr implements Iterator<Optional<A>> {
        private ItrState state = closed
                ? ItrState.EXCEEDED
                : ItrState.WAIT_NEXT_VALUE;
        private final Lock itrLock = new ReentrantLock();
        @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
        private Optional<A> nextValue;

        @Override
        public boolean hasNext() {
            boolean result = false;
            try {
                itrLock.lockInterruptibly();
                result = hasNextBody();
            } catch (InterruptedException e) {
                portLock.lock();
                purge();
                portLock.unlock();
                state = ItrState.EXCEEDED;
                Thread.currentThread().interrupt();
            } finally {
                itrLock.unlock();
            }
            return result;
        }

        private boolean hasNextBody() throws InterruptedException {
            final boolean result;
            switch (state) {
                case EXCEEDED:
                    result = false;
                    break;
                case HAS_NEXT_VALUE:
                    result = true;
                    break;
                case WAIT_NEXT_VALUE:
                    if (closed && queue.isEmpty()) {
                        state = ItrState.EXCEEDED;
                        result = false;
                    } else {
                        nextValue = queue.take();
                        result = nextValue != Optional.empty();
                        state = result ? ItrState.HAS_NEXT_VALUE : ItrState.EXCEEDED;
                    }
                    break;
                default:
                    state = ItrState.EXCEEDED;
                    result = false;
            }
            return result;
        }

        @Override
        public Optional<A> next() {
            try {
                itrLock.lockInterruptibly();
                switch (state) {
                    case EXCEEDED:
                        throw new NoSuchElementException("Port is closed");
                    case HAS_NEXT_VALUE:
                        state = ItrState.WAIT_NEXT_VALUE;
                        break;
                    case WAIT_NEXT_VALUE:
                        if (!hasNextBody()) {
                            throw new NoSuchElementException("Port is closed");
                        }
                        state = ItrState.WAIT_NEXT_VALUE;
                        break;
                }
            } catch (InterruptedException e) {
                queue.clear();
                state = ItrState.EXCEEDED;
                Thread.currentThread().interrupt();
                throw new NoSuchElementException("Port is closed");
            } finally {
                itrLock.unlock();
            }
            return nextValue;
        }


    }

    private enum ItrState {EXCEEDED, WAIT_NEXT_VALUE, HAS_NEXT_VALUE}

}