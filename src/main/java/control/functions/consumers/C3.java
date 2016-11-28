package control.functions.consumers;

import control.functions.F3;
import data.tuple.T0;
import data.tuple.T1;
import data.tuple.T2;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 19:27)
 */
@FunctionalInterface
public interface C3<A, B, C> extends F3<A, B, C, Void> {
    void uAccept(A a, B b, C c) throws Throwable;

    @Override
    default Void uApply(A a, B b, C c) throws Throwable {
        uAccept(a, b, c);
        return null;
    }

    @Override
    default C1<C> a(A a, B b) {
        return c -> uAccept(a, b, c);
    }

    @Override
    default C2<B, C> a(A a) {
        return (b, c) -> uAccept(a, b, c);
    }

    @Override
    default C3<A, B, C> a() {
        return this;
    }

    @Override
    default C1<C> a(T2<? extends A, ? extends B> t) {
        return a(t.a, t.b);
    }

    @Override
    default C2<B, C> a(T1<? extends A> t) {
        return a(t.a);
    }

    @Override
    default C3<A, B, C> a(T0 __) {
        return a();
    }

    @Override
    default C3<B, A, C> fp12() {
        return (b, a, c) -> uAccept(a, b, c);
    }

    @Override
    default C3<C, B, A> fp13() {
        return (c, b, a) -> uAccept(a, b, c);
    }

    @Override
    default C3<A, C, B> fp23() {
        return (a, c, b) -> uAccept(a, b, c);
    }
}
