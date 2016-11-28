package control.functions;

import data.tuple.T0;

import java.util.function.Supplier;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 19:30)
 */
@FunctionalInterface
public interface Sr<Rt> extends Supplier<Rt> {
    Rt uApply() throws Throwable;

    default Rt a() {
        try {
            return uApply();
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    default Rt a(T0 __) {
        return a();
    }

    @Override
    default Rt get() {
        return a();
    }

    static <Rt> Rt a(Sr<? extends Rt> sr) {
        return sr.a();
    }

}
