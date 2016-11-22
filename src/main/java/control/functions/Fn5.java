package control.functions;

import data.tuple.T0;
import data.tuple.T1;
import data.tuple.T2;
import data.tuple.T3;
import data.tuple.T5;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-22 13:50)
 */
@FunctionalInterface
public interface Fn5<A, B, C, D, E, Rt> extends Fn4<A, B, C, D, Fn1<E, Rt>> {
    Rt uApply(A a, B b, C c, D d, E e) throws Throwable;

    @Override
    default Fn1<E, Rt> uApply(A a, B b, C c, D d) {
        return e -> uApply(a, b, c, d, e);
    }

    default Rt a(A a, B b, C c, D d, E e) {
        return Sup.a(() -> uApply(a, b, c, d, e));
    }

    @Override
    default Fn2<D, E, Rt> a(A a, B b, C c) {
        return (d, e) -> a(a, b, c, d, e);
    }

    @Override
    default Fn3<C, D, E, Rt> a(A a, B b) {
        return (c, d, e) -> a(a, b, c, d, e);
    }

    @Override
    default Fn4<B, C, D, E, Rt> a(A a) {
        return (b, c, d, e) -> a(a, b, c, d, e);
    }

    @Override
    default Fn5<A, B, C, D, E, Rt> a() {
        return this;
    }

    default Rt a(T5<? extends A, ? extends B, ? extends C, ? extends D, ? extends E> t) {
        return a(t.a, t.b, t.c, t.d, t.e);
    }

    @Override
    default Fn2<D, E, Rt> a(T3<? extends A, ? extends B, ? extends C> t) {
        return a(t.a, t.b, t.c);
    }

    @Override
    default Fn3<C, D, E, Rt> a(T2<? extends A, ? extends B> t) {
        return a(t.a, t.b);
    }

    @Override
    default Fn4<B, C, D, E, Rt> a(T1<? extends A> t) {
        return a(t.a);
    }

    @Override
    default Fn5<A, B, C, D, E, Rt> a(T0 __) {
        return a();
    }
}
