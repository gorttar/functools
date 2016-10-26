package concurrent.port;

import data.Pair;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * optimized port implementation based on {@link BlockingQueue} without wrapping messages
 * uses {@link #CLOSE_VALUE} as port close signal
 *
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-15)
 */
@SuppressWarnings("WeakerAccess")
public class OptimizedBufferedPort<T> implements Port<T> {
    private final static Object CLOSE_VALUE = new Object();
    private boolean closed = false;
    private final Lock portLock = new ReentrantLock();
    private final BlockingQueue<T> queue;
    private final BlockingQueue rawQueue;
    final PortItr portItr;

    OptimizedBufferedPort(int bufferSize) {
        queue = new ArrayBlockingQueue<>(bufferSize);
        rawQueue = queue;
        portItr = new PortItr();
    }

    @Override
    public void send(@Nonnull T message) throws InterruptedException {
        try {
            portLock.lockInterruptibly();
            if (closed) {
                throw new IllegalStateException("Port is closed");
            }
            queue.put(message);
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
            //noinspection unchecked
            rawQueue.put(CLOSE_VALUE);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        closed = true;
    }

    @Override
    public Response sendIfOpen(@Nonnull T message) throws InterruptedException {
        try {
            portLock.lockInterruptibly();
            if (!closed) {
                queue.put(message);
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
    public Response sendImmediate(@Nonnull T message) throws IllegalStateException {
        if (closed) {
            throw new IllegalStateException("Port is closed");
        }
        return queue.offer(message) ? Response.OK : Response.UNABLE_TO_RECEIVE_NOW;
    }

    @Override
    public Response sendImmediateIfOpen(@Nonnull T message) {
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
                //noinspection unchecked
                rawQueue.put(CLOSE_VALUE);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            portLock.unlock();
        }
    }

    public static <T> Pair<Port<T>, Stream<T>> createPortWithStream(int bufferSize) {
        final OptimizedBufferedPort<T> port = new OptimizedBufferedPort<>(bufferSize);
        return Pair.tup(
                port,
                StreamSupport.stream(
                        Spliterators.spliterator(port.portItr, -1, Spliterator.IMMUTABLE),
                        false));
    }

    private class PortItr implements Iterator<T> {
        private ItrState state = closed
                ? ItrState.EXCEEDED
                : ItrState.WAIT_NEXT_VALUE;
        private final Lock itrLock = new ReentrantLock();
        private T nextValue;

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
                        result = nextValue != CLOSE_VALUE;
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
        public T next() {
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