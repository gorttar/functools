package concurrent.port;

import java.io.Closeable;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-19)
 */
public interface Port<T> extends Closeable {
    void send(T message) throws InterruptedException, IllegalStateException;
    boolean sendIfOpen(T message) throws InterruptedException;
    int remainingCapacity();
    boolean sendImmediate(T message) throws IllegalStateException;
    Response sendImmediateIfOpen(T message);

    enum Response{OK, CLOSED, FULL}
}