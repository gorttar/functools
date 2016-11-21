package control.functions;

import data.tuple.T0;
import data.tuple.T1;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 19:27)
 */
@FunctionalInterface
public interface Co3<A, B, C> extends Fn2<A, B, Co1<C>> {
    void a(A a, B b, C c);

    @Override
    default Co1<C> a(A a, B b) {
        return c -> a(a, b, c);
    }

    @Override
    default Co2<B, C> a(A a) {
        return (b, c) -> a(a, b, c);
    }

    @Override
    default Co3<A, B, C> a() {
        return this;
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
