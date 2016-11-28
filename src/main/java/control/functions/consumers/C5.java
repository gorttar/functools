package control.functions.consumers;

import control.functions.F5;
import data.tuple.T0;
import data.tuple.T1;
import data.tuple.T2;
import data.tuple.T3;
import data.tuple.T4;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-22 14:29)
 */
@FunctionalInterface
public interface C5<A, B, C, D, E> extends F5<A, B, C, D, E, Void> {
    void uAccept(A a, B b, C c, D d, E e) throws Throwable;

    @Override
    default Void uApply(A a, B b, C c, D d, E e) throws Throwable {
        uAccept(a, b, c, d, e);
        return null;
    }

    @Override
    default C1<E> a(A a, B b, C c, D d) {
        return e -> a(a, b, c, d, e);
    }

    @Override
    default C2<D, E> a(A a, B b, C c) {
        return (d, e) -> a(a, b, c, d, e);
    }

    @Override
    default C3<C, D, E> a(A a, B b) {
        return (c, d, e) -> a(a, b, c, d, e);
    }

    @Override
    default C4<B, C, D, E> a(A a) {
        return (b, c, d, e) -> a(a, b, c, d, e);
    }

    @Override
    default C5<A, B, C, D, E> a() {
        return this;
    }

    @Override
    default C1<E> a(T4<? extends A, ? extends B, ? extends C, ? extends D> t) {
        return a(t.a, t.b, t.c, t.d);
    }

    @Override
    default C2<D, E> a(T3<? extends A, ? extends B, ? extends C> t) {
        return a(t.a, t.b, t.c);
    }

    @Override
    default C3<C, D, E> a(T2<? extends A, ? extends B> t) {
        return a(t.a, t.b);
    }

    @Override
    default C4<B, C, D, E> a(T1<? extends A> t) {
        return a(t.a);
    }

    @Override
    default C5<A, B, C, D, E> a(T0 __) {
        return a();
    }

    @Override
    default C5<B, A, C, D, E> fp12() {
        return (b, a, c, d, e) -> uAccept(a, b, c, d, e);
    }

    @Override
    default C5<C, B, A, D, E> fp13() {
        return (c, b, a, d, e) -> uAccept(a, b, c, d, e);
    }

    @Override
    default C5<A, C, B, D, E> fp23() {
        return (a, c, b, d, e) -> uAccept(a, b, c, d, e);
    }

    @Override
    default C5<D, B, C, A, E> fp14() {
        return (d, b, c, a, e) -> uAccept(a, b, c, d, e);
    }

    @Override
    default C5<A, D, C, B, E> fp24() {
        return (a, d, c, b, e) -> uAccept(a, b, c, d, e);
    }

    @Override
    default C5<A, B, D, C, E> fp34() {
        return (a, b, d, c, e) -> uAccept(a, b, c, d, e);
    }

    @Override
    default C5<E, B, C, D, A> fp15() {
        return (e, b, c, d, a) -> uAccept(a, b, c, d, e);
    }

    @Override
    default C5<A, E, C, D, B> fp25() {
        return (a, e, c, d, b) -> uAccept(a, b, c, d, e);
    }

    @Override
    default C5<A, B, E, D, C> fp35() {
        return (a, b, e, d, c) -> uAccept(a, b, c, d, e);
    }

    @Override
    default C5<A, B, C, E, D> fp45() {
        return (a, b, c, e, d) -> uAccept(a, b, c, d, e);
    }
}
