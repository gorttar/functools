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
public interface F5<A, B, C, D, E, Rt> extends F4<A, B, C, D, F1<E, Rt>> {
    Rt uApply(A a, B b, C c, D d, E e) throws Throwable;

    @Override
    default F1<E, Rt> uApply(A a, B b, C c, D d) {
        return e -> uApply(a, b, c, d, e);
    }

    default Rt a(A a, B b, C c, D d, E e) {
        return Sr.a(() -> uApply(a, b, c, d, e));
    }

    @Override
    default F2<D, E, Rt> a(A a, B b, C c) {
        return (d, e) -> a(a, b, c, d, e);
    }

    @Override
    default F3<C, D, E, Rt> a(A a, B b) {
        return (c, d, e) -> a(a, b, c, d, e);
    }

    @Override
    default F4<B, C, D, E, Rt> a(A a) {
        return (b, c, d, e) -> a(a, b, c, d, e);
    }

    @Override
    default F5<A, B, C, D, E, Rt> a() {
        return this;
    }

    default Rt a(T5<? extends A, ? extends B, ? extends C, ? extends D, ? extends E> t) {
        return a(t.a, t.b, t.c, t.d, t.e);
    }

    @Override
    default F2<D, E, Rt> a(T3<? extends A, ? extends B, ? extends C> t) {
        return a(t.a, t.b, t.c);
    }

    @Override
    default F3<C, D, E, Rt> a(T2<? extends A, ? extends B> t) {
        return a(t.a, t.b);
    }

    @Override
    default F4<B, C, D, E, Rt> a(T1<? extends A> t) {
        return a(t.a);
    }

    @Override
    default F5<A, B, C, D, E, Rt> a(T0 __) {
        return a();
    }

    @Override
    default F5<B, A, C, D, E, Rt> fp12() {
        return (b, a, c, d, e) -> uApply(a, b, c, d, e);
    }

    @Override
    default F5<C, B, A, D, E, Rt> fp13() {
        return (c, b, a, d, e) -> uApply(a, b, c, d, e);
    }

    @Override
    default F5<A, C, B, D, E, Rt> fp23() {
        return (a, c, b, d, e) -> uApply(a, b, c, d, e);
    }

    @Override
    default F5<D, B, C, A, E, Rt> fp14() {
        return (d, b, c, a, e) -> uApply(a, b, c, d, e);
    }

    @Override
    default F5<A, D, C, B, E, Rt> fp24() {
        return (a, d, c, b, e) -> uApply(a, b, c, d, e);
    }

    @Override
    default F5<A, B, D, C, E, Rt> fp34() {
        return (a, b, d, c, e) -> uApply(a, b, c, d, e);
    }

    default F5<E, B, C, D, A, Rt> fp15() {
        return (e, b, c, d, a) -> uApply(a, b, c, d, e);
    }

    default F5<A, E, C, D, B, Rt> fp25() {
        return (a, e, c, d, b) -> uApply(a, b, c, d, e);
    }

    default F5<A, B, E, D, C, Rt> fp35() {
        return (a, b, e, d, c) -> uApply(a, b, c, d, e);
    }

    default F5<A, B, C, E, D, Rt> fp45() {
        return (a, b, c, e, d) -> uApply(a, b, c, d, e);
    }

}
