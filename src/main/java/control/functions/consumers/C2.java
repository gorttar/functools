package control.functions.consumers;

import control.functions.F2;
import data.tuple.T0;
import data.tuple.T1;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 19:24)
 */
@FunctionalInterface
public interface C2<A, B> extends F2<A, B, Void> {
    void uAccept(A a, B b) throws Throwable;

    @Override
    default Void uApply(A a, B b) throws Throwable {
        uAccept(a, b);
        return null;
    }

    @Override
    default C1<B> a(A a) {
        return b -> uApply(a, b);
    }

    @Override
    default C2<A, B> a() {
        return this;
    }

    @Override
    default C1<B> a(T1<? extends A> t) {
        return a(t.a);
    }

    @Override
    default C2<A, B> a(T0 __) {
        return a();
    }

    @Override
    default C2<B, A> fp12() {
        return (b, a) -> uAccept(a, b);
    }
}
