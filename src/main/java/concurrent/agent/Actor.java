package concurrent.agent;

import concurrent.port.Port;

/**
 * interface to represent actor (something with port for sending messages to it)
 * should be implemented by classes which process sequence of messages
 *
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-24 17:47)
 */
public interface Actor<A> {
    /**
     * @return actor's port
     */
    Port<A> port();
}