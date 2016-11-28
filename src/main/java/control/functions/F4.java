package control.functions;

import data.tuple.T0;
import data.tuple.T1;
import data.tuple.T2;
import data.tuple.T4;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 19:10)
 */
@FunctionalInterface
public interface F4<A, B, C, D, Rt> extends F3<A, B, C, F1<D, Rt>> {
    Rt uApply(A a, B b, C c, D d) throws Throwable;

    @Override
    default F1<D, Rt> uApply(A a, B b, C c) throws Throwable {
        return d -> uApply(a, b, c, d);
    }

    default Rt a(A a, B b, C c, D d) {
        return Sr.a(() -> uApply(a, b, c, d));
    }

    @Override
    default F2<C, D, Rt> a(A a, B b) {
        return (c, d) -> a(a, b, c, d);
    }

    @Override
    default F3<B, C, D, Rt> a(A a) {
        return (b, c, d) -> a(a, b, c, d);
    }

    @Override
    default F4<A, B, C, D, Rt> a() {
        return this;
    }

    default Rt a(T4<? extends A, ? extends B, ? extends C, ? extends D> t) {
        return a(t.a, t.b, t.c, t.d);
    }

    @Override
    default F2<C, D, Rt> a(T2<? extends A, ? extends B> t) {
        return a(t.a, t.b);
    }

    @Override
    default F3<B, C, D, Rt> a(T1<? extends A> t) {
        return a(t.a);
    }

    @Override
    default F4<A, B, C, D, Rt> a(T0 __) {
        return a();
    }

    @Override
    default F4<B, A, C, D, Rt> fp12() {
        return (b, a, c, d) -> uApply(a, b, c, d);
    }

    @Override
    default F4<C, B, A, D, Rt> fp13() {
        return (c, b, a, d) -> uApply(a, b, c, d);
    }

    @Override
    default F4<A, C, B, D, Rt> fp23() {
        return (a, c, b, d) -> uApply(a, b, c, d);
    }

    default F4<D, B, C, A, Rt> fp14() {
        return (d, b, c, a) -> uApply(a, b, c, d);
    }

    default F4<A, D, C, B, Rt> fp24() {
        return (a, d, c, b) -> uApply(a, b, c, d);
    }

    default F4<A, B, D, C, Rt> fp34() {
        return (a, b, d, c) -> uApply(a, b, c, d);
    }

}
