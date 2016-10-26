package concurrent.port;

import javax.annotation.Nonnull;
import java.io.Closeable;

/**
 * interface to represent port (something for sending messages to it).
 * Can be closed in order to signal that there is no more messages to send.
 *
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-19)
 */
public interface Port<T> extends Closeable {
    /**
     * sends message to port. Will block invoking thread until port will be able to receive message
     *
     * @param message to be sent
     * @throws InterruptedException  if interruption was occurred during wait for port to receive message
     * @throws IllegalStateException if port is closed
     */
    void send(@Nonnull T message) throws InterruptedException, IllegalStateException;

    /**
     * sends message to port if it's open. Will block invoking thread until port will be able to receive message
     *
     * @param message to be sent
     * @return true if message was sent successfully
     * @throws InterruptedException if interruption was occurred during wait for port to receive message
     */
    Response sendIfOpen(@Nonnull T message) throws InterruptedException;

    /**
     * @return number of messages port can receive right now without blockage
     */
    int remainingCapacity();

    /**
     * sends message to port without waiting for it to be able to receive.
     *
     * @param message to be sent
     * @return true if message was sent successfully
     * @throws IllegalStateException if port is closed
     */
    Response sendImmediate(@Nonnull T message) throws IllegalStateException;

    /**
     * sends message to port if it's open without waiting for it to be able to receive.
     *
     * @param message to be sent
     * @return response which can be used to determine result of method invocation
     */
    Response sendImmediateIfOpen(@Nonnull T message);

    /**
     * enum to encode port responses
     */
    enum Response {
        OK, CLOSED, UNABLE_TO_RECEIVE_NOW
    }
}