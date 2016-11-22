package control.functions.consumers;

import control.functions.Fn2;
import data.tuple.T0;
import data.tuple.T1;
import data.tuple.T3;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 19:27)
 */
@FunctionalInterface
public interface Co3<A, B, C> extends Fn2<A, B, Co1<C>> {
    void uAccept(A a, B b, C c) throws Throwable;

    @Override
    default Co1<C> uApply(A a, B b) throws Throwable {
        return c -> uAccept(a, b, c);
    }

    default Void a(A a, B b, C c) {
        return a(a, b).a(c);
    }

    @Override
    default Co2<B, C> a(A a) {
        return (b, c) -> uAccept(a, b, c);
    }

    @Override
    default Co3<A, B, C> a() {
        return this;
    }

    default Void a(T3<? extends A, ? extends B, ? extends C> t) {
        return a(t.a, t.b, t.c);
    }

    @Override
    default Co2<B, C> a(T1<? extends A> t) {
        return a(t.a);
    }

    @Override
    default Co3<A, B, C> a(T0 __) {
        return a();
    }
}
