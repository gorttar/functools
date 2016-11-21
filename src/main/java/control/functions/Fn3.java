package control.functions;

import data.tuple.T3;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 19:02)
 */
@FunctionalInterface
public interface Fn3<A, B, C, Rt> extends Fn2<A, B, Fn1<C, Rt>> {
    Rt a(A a, B b, C c);

    @Override
    default Fn2<B, C, Rt> apply(A a) {
        return (b, c) -> a(a, b, c);
    }

    @Override
    default Fn1<C, Rt> a(A a, B b) {
        return c -> a(a, b, c);
    }

    @Override
    default Fn2<B, C, Rt> a(A a) {
        return (b, c) -> a(a, b, c);
    }

    default Rt a(T3<? extends A, ? extends B, ? extends C> t) {
        return a(t.a, t.b, t.c);
    }
}
