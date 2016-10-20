package concurrent.port;

import data.Pair;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Andrey Antipov (andrey.antipov@cxense.com) (2016-10-20 18:27)
 */
public class OptionalBufferedPort<T> implements Port<T> {
    private final RawBufferedPort<Optional<T>> delegate;

    private OptionalBufferedPort(int bufferSize) {
        delegate = new RawBufferedPort<>(Optional.empty(), bufferSize);
    }

    @Override
    public void send(T message) throws InterruptedException, IllegalStateException {
        delegate.send(Optional.of(message));
    }

    @Override
    public boolean sendIfOpen(T message) throws InterruptedException {
        return delegate.sendIfOpen(Optional.of(message));
    }

    @Override
    public int remainingCapacity() {
        return delegate.remainingCapacity();
    }

    @Override
    public boolean sendImmediate(T message) throws IllegalStateException {
        return delegate.sendImmediate(Optional.of(message));
    }

    @Override
    public Response sendImmediateIfOpen(T message) {
        return delegate.sendImmediateIfOpen(Optional.of(message));
    }

    @Override
    public void close() throws IOException {
        delegate.close();
    }

    public static <T> Pair<Port<T>, Stream<T>> createPortWithStream(int bufferSize) {
        final OptionalBufferedPort<T> port = new OptionalBufferedPort<>(bufferSize);
        final Iterator<Optional<T>> portItr = port.delegate.portItr;
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

}