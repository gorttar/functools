package control.functions;

import data.tuple.T2;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 17:18)
 */
@FunctionalInterface
public interface Fn2<A, B, Rt> extends Fn1<A, Fn1<B, Rt>> {
    Rt a(A a, B b);

    @Override
    default Fn1<B, Rt> apply(A a) {
        return b -> a(a, b);
    }

    default Rt a(T2<? extends A, ? extends B> t) {
        return a(t.a, t.b);
    }
}
