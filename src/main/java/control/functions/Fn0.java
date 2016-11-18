package control.functions;

import java.util.function.Supplier;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 19:30)
 */
@FunctionalInterface
public interface Fn0<Rt> extends Supplier<Rt> {
    Rt g();

    @Override
    default Rt get() {
        return g();
    }
}
