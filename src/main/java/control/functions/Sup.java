package control.functions;

import data.tuple.T0;

import java.util.function.Supplier;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 19:30)
 */
@FunctionalInterface
public interface Sup<Rt> extends Supplier<Rt> {
    Rt a();

    default Rt a(T0 __) {
        return a();
    }

    @Override
    default Rt get() {
        return a();
    }
}
