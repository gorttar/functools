package control.functions;

import data.tuple.T0;
import data.tuple.T1;
import data.tuple.T3;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 19:02)
 */
@FunctionalInterface
public interface F3<A, B, C, Rt> extends F2<A, B, F1<C, Rt>> {
    Rt uApply(A a, B b, C c) throws Throwable;

    @Override
    default F1<C, Rt> uApply(A a, B b) throws Throwable {
        return c -> uApply(a, b, c);
    }

    default Rt a(A a, B b, C c) {
        return Sr.a(() -> uApply(a, b, c));
    }

    @Override
    default F2<B, C, Rt> a(A a) {
        return (b, c) -> a(a, b, c);
    }

    @Override
    default F3<A, B, C, Rt> a() {
        return this;
    }

    default Rt a(T3<? extends A, ? extends B, ? extends C> t) {
        return a(t.a, t.b, t.c);
    }

    @Override
    default F2<B, C, Rt> a(T1<? extends A> t) {
        return a(t.a);
    }

    @Override
    default F3<A, B, C, Rt> a(T0 __) {
        return a();
    }

    @Override
    default F3<B, A, C, Rt> fp12() {
        return (b, a, c) -> uApply(a, b, c);
    }

    default F3<C, B, A, Rt> fp13() {
        return (c, b, a) -> uApply(a, b, c);
    }

    default F3<A, C, B, Rt> fp23() {
        return (a, c, b) -> uApply(a, b, c);
    }

}
