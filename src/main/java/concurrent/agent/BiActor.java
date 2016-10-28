package concurrent.agent;

import concurrent.port.Port;

/**
 * interface to represent bi actor (something with two ports for sending messages to it)
 * should be implemented by classes which process two sequences of messages together (join, zip, merge etc.)
 *
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-24 17:47)
 */
public interface BiActor<A, B> extends Runnable {
    /**
     * @return bi actor's left port
     */
    Port<A> leftPort();

    /**
     * @return bi actor's right port
     */
    Port<B> rightPort();
}