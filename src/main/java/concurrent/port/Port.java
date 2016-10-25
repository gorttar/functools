package concurrent.port;

import javax.annotation.Nonnull;
import java.io.Closeable;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-19)
 */
public interface Port<T> extends Closeable {
    void send(@Nonnull T message) throws InterruptedException, IllegalStateException;

    boolean sendIfOpen(@Nonnull T message) throws InterruptedException;

    int remainingCapacity();

    boolean sendImmediate(@Nonnull T message);

    Response sendImmediateIfOpen(@Nonnull T message);

    enum Response {OK, CLOSED, FULL}
}