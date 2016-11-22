package control.functions;

import data.tuple.T0;
import data.tuple.T1;
import data.tuple.T2;
import data.tuple.T4;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 19:10)
 */
@FunctionalInterface
public interface Fn4<A, B, C, D, Rt> extends Fn3<A, B, C, Fn1<D, Rt>> {
    Rt uApply(A a, B b, C c, D d) throws Throwable;

    @Override
    default Fn1<D, Rt> uApply(A a, B b, C c) throws Throwable {
        return d -> uApply(a, b, c, d);
    }

    default Rt a(A a, B b, C c, D d) {
        return Sup.a(() -> uApply(a, b, c, d));
    }

    @Override
    default Fn2<C, D, Rt> a(A a, B b) {
        return (c, d) -> a(a, b, c, d);
    }

    @Override
    default Fn3<B, C, D, Rt> a(A a) {
        return (b, c, d) -> a(a, b, c, d);
    }

    @Override
    default Fn4<A, B, C, D, Rt> a() {
        return this;
    }

    default Rt a(T4<? extends A, ? extends B, ? extends C, ? extends D> t) {
        return a(t.a, t.b, t.c, t.d);
    }

    @Override
    default Fn2<C, D, Rt> a(T2<? extends A, ? extends B> t) {
        return a(t.a, t.b);
    }

    @Override
    default Fn3<B, C, D, Rt> a(T1<? extends A> t) {
        return a(t.a);
    }

    @Override
    default Fn4<A, B, C, D, Rt> a(T0 __) {
        return a();
    }
}
