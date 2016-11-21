package control.functions;

import data.tuple.T4;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 19:10)
 */
@FunctionalInterface
public interface Fn4<A, B, C, D, Rt> extends Fn3<A, B, C, Fn1<D, Rt>> {
    Rt a(A a, B b, C c, D d);

    @Override
    default Fn1<D, Rt> a(A a, B b, C c) {
        return d -> a(a, b, c, d);
    }

    @Override
    default Fn2<C, D, Rt> a(A a, B b) {
        return (c, d) -> a(a, b, c, d);
    }

    @Override
    default Fn3<B, C, D, Rt> a(A a) {
        return (b, c, d) -> a(a, b, c, d);
    }

    default Rt a(T4<? extends A, ? extends B, ? extends C, ? extends D> t) {
        return a(t.a, t.b, t.c, t.d);
    }

    @Override
    default Fn3<B, C, D, Rt> apply(A a) {
        return (b, c, d) -> a(a, b, c, d);
    }
}
