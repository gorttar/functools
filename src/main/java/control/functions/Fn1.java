package control.functions;

import java.util.function.Function;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 17:17)
 */
@FunctionalInterface
public interface Fn1<A, Rt> extends Function<A, Rt> {
    Rt a(A a);

    @Override
    default Rt apply(A a) {
        return a(a);
    }
}
