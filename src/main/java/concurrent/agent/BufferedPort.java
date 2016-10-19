package concurrent.agent;

import static concurrent.agent.Port.Response.CLOSED;
import static concurrent.agent.Port.Response.FULL;
import static concurrent.agent.Port.Response.OK;
import static data.Pair.tup;

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
public class BufferedPort<T> implements Port<T> {
    private boolean closed = false;
    private final Lock portLock = new ReentrantLock();
    private final BlockingQueue<T> queue;
    private final PortItr portItr;

    private BufferedPort(int bufferSize) {
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

    private void sendBody(T message) throws InterruptedException {
        if (closed) {
            throw new IllegalStateException("Port is closed");
        }
        if (message == null) {
            closed = true;
        }
        queue.put(message);
    }

    @Override
    public int remainingCapacity() {
        return queue.remainingCapacity();
    }

    @Override
    public boolean sendImmediate(T message) {
        return queue.add(message);
    }

    @Override
    public Response sendImmediateIfOpen(T message) {
        final Response result;
        portLock.lock();
        if (closed) {
            result = CLOSED;
        } else {
            result = sendImmediate(message) ? OK : FULL;
        }
        portLock.unlock();
        return result;
    }

    @Override
    public void close() throws IOException {
        portLock.lock();
        closed = true;
        portLock.unlock();
    }

    public static <T> Pair<Port<T>, Stream<T>> createPortWithStream(int bufferSize) {
        final BufferedPort<T> port = new BufferedPort<>(bufferSize);
        return tup(
                port,
                StreamSupport.stream(
                        Spliterators.spliterator(port.portItr, -1, Spliterator.IMMUTABLE),
                        false));
    }

    private class PortItr implements Iterator<T> {
        private ItrState state = closed
                ? ItrState.EXCEEDED
                : ItrState.WAIT_NEXT_VALUE;
        private Lock itrLock = new ReentrantLock();
        private T nextValue;

        @Override
        public boolean hasNext() {
            boolean result = false;
            try {
                itrLock.lockInterruptibly();
                result = hasNextBody();
            } catch (InterruptedException e) {
                state = ItrState.EXCEEDED;
                Thread.currentThread().interrupt();
            } finally {
                itrLock.unlock();
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
                state = ItrState.EXCEEDED;
                Thread.currentThread().interrupt();
                throw new NoSuchElementException("Port is closed");
            } finally {
                itrLock.unlock();
            }
            return nextValue;
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
                        result = true;
                        state = ItrState.HAS_NEXT_VALUE;
                    }
                    break;
                default:
                    state = ItrState.EXCEEDED;
                    result = false;
            }
            return result;
        }

    }

    private enum ItrState {EXCEEDED, WAIT_NEXT_VALUE, HAS_NEXT_VALUE}

}