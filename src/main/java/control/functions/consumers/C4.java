package control.functions.consumers;

import control.functions.F4;
import data.tuple.T0;
import data.tuple.T1;
import data.tuple.T2;
import data.tuple.T3;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-21 21:50)
 */
@FunctionalInterface
public interface C4<A, B, C, D> extends F4<A, B, C, D, Void> {
    void uAccept(A a, B b, C c, D d) throws Throwable;

    @Override
    default Void uApply(A a, B b, C c, D d) throws Throwable {
        uAccept(a, b, c, d);
        return null;
    }

    @Override
    default C1<D> a(A a, B b, C c) {
        return d -> a(a, b, c, d);
    }

    @Override
    default C2<C, D> a(A a, B b) {
        return (c, d) -> a(a, b, c, d);
    }

    @Override
    default C3<B, C, D> a(A a) {
        return (b, c, d) -> a(a, b, c, d);
    }

    @Override
    default C4<A, B, C, D> a() {
        return this;
    }

    @Override
    default C1<D> a(T3<? extends A, ? extends B, ? extends C> t) {
        return a(t.a, t.b, t.c);
    }

    @Override
    default C2<C, D> a(T2<? extends A, ? extends B> t) {
        return a(t.a, t.b);
    }

    @Override
    default C3<B, C, D> a(T1<? extends A> t) {
        return a(t.a);
    }

    @Override
    default C4<A, B, C, D> a(T0 __) {
        return a();
    }

    @Override
    default C4<B, A, C, D> fp12() {
        return (b, a, c, d) -> uAccept(a, b, c, d);
    }

    @Override
    default C4<C, B, A, D> fp13() {
        return (c, b, a, d) -> uAccept(a, b, c, d);
    }

    @Override
    default C4<A, C, B, D> fp23() {
        return (a, c, b, d) -> uAccept(a, b, c, d);
    }

    @Override
    default C4<D, B, C, A> fp14() {
        return (d, b, c, a) -> uAccept(a, b, c, d);
    }

    @Override
    default C4<A, D, C, B> fp24() {
        return (a, d, c, b) -> uAccept(a, b, c, d);
    }

    @Override
    default C4<A, B, D, C> fp34() {
        return (a, b, d, c) -> uAccept(a, b, c, d);
    }
}
