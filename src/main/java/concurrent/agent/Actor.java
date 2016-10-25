package concurrent.agent;

import concurrent.port.Port;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-24 17:47)
 */
public interface Actor<A> {
    Port<A> port();
}