package concurrent.port;

import data.Pair;

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
 * @author Andrey Antipov (andrey.antipov@maxifier.com) (2016-10-15)
 */
@SuppressWarnings("WeakerAccess")
public class OptimizedBufferedPort<T> implements Port<T> {
    private final static Object CLOSE_VALUE = new Object();
    private boolean closed = false;
    private final Lock portLock = new ReentrantLock();
    private final BlockingQueue queue;
    final PortItr portItr;

    OptimizedBufferedPort(int bufferSize) {
        queue = new ArrayBlockingQueue<>(bufferSize);
        portItr = new PortItr();
    }

    @Override
    public void send(T message) throws InterruptedException {
        try {
            portLock.lockInterruptibly();
            sendBody(message);
        } catch (Exception e) {
            // if we are receiving any exception then we should cleanup queue and close port
            queue.clear();
            closed = true;
            throw e;
        } finally {
            portLock.unlock();
        }
    }

    @Override
    public boolean sendIfOpen(T message) throws InterruptedException {
        try {
            portLock.lockInterruptibly();
            if (!closed) {
                sendBody(message);
            }
        } catch (Exception e) {
            // if we are receiving any exception then we should cleanup queue and close port
            queue.clear();
            closed = true;
            throw e;
        } finally {
            portLock.unlock();
        }
        return !closed;
    }

    private void sendBody(Object message) throws InterruptedException {
        if (closed) {
            throw new IllegalStateException("Port is closed");
        }
        if (message == null) {
            closed = true;
        }
        //noinspection unchecked
        queue.put(message);
    }

    @Override
    public int remainingCapacity() {
        return queue.remainingCapacity();
    }

    @Override
    public boolean sendImmediate(T message) {
        //noinspection unchecked
        return queue.add(message);
    }

    @Override
    public Response sendImmediateIfOpen(T message) {
        final Response result;
        portLock.lock();
        if (closed) {
            result = Response.CLOSED;
        } else {
            result = sendImmediate(message) ? Response.OK : Response.FULL;
        }
        portLock.unlock();
        return result;
    }

    @Override
    public void close() throws IOException {
        portLock.lock();
        try {
            sendBody(CLOSE_VALUE);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            closed = true;
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
                queue.clear();
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
                        //noinspection unchecked
                        nextValue = (T) queue.take();
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