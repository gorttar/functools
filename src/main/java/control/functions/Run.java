package control.functions;

import data.tuple.T0;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-21 21:35)
 */
@FunctionalInterface
public interface Run extends Runnable, Sup<Void> {
    default Void a() {
        run();
        return null;
    }

    default Void a(T0 __) {
        return a();
    }
}
