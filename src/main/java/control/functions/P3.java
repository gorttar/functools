package control.functions;

import data.tuple.T0;
import data.tuple.T1;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-23 15:07)
 */
@FunctionalInterface
public interface P3<A, B, C> extends F2<A, B, P1<C>> {
    boolean uTest(A a, B b, C c) throws Throwable;

    @Override
    default P1<C> uApply(A a, B b) throws Throwable {
        return c -> uTest(a, b, c);
    }

    default Boolean a(A a, B b, C c) {
        return a(a, b).a(c);
    }

    @Override
    default P2<B, C> a(A a) {
        return (b, c) -> uTest(a, b, c);
    }

    @Override
    default P3<A, B, C> a() {
        return this;
    }

    @Override
    default P2<B, C> a(T1<? extends A> t) {
        return a(t.a);
    }

    @Override
    default P3<A, B, C> a(T0 __) {
        return a();
    }

    @Override
    default P3<B, A, C> fp12() {
        return (b, a, c) -> uTest(a, b, c);
    }

    default P3<C, B, A> fp13() {
        return (c, b, a) -> uTest(a, b, c);
    }

    default P3<A, C, B> fp23() {
        return (a, c, b) -> uTest(a, b, c);
    }
}
