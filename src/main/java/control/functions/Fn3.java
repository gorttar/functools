package control.functions;

import data.tuple.T2;
import data.tuple.T3;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 19:02)
 */
@FunctionalInterface
public interface Fn3<A, B, C, Rt> extends Fn1<A, Fn2<B, C, Rt>> {
    Rt a(A a, B b, C c);

    default Rt a(T3<? extends A, ? extends B, ? extends C> t) {
        return a(t.a, t.b, t.c);
    }

    default Fn1<C, Rt> a(A a, B b) {
        return c -> a(a, b, c);
    }

    default Fn1<C, Rt> a(T2<? extends A, ? extends B> t) {
        return a(t.a, t.b);
    }

    @Override
    default Fn2<B, C, Rt> apply(A a) {
        return (b, c) -> a(a, b, c);
    }
}
