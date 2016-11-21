package control.functions;

import data.tuple.T0;
import data.tuple.T1;
import data.tuple.T2;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-21 21:50)
 */
@FunctionalInterface
public interface Co4<A, B, C, D> extends Fn3<A, B, C, Co1<D>> {
    void a(A a, B b, C c, D d);

    @Override
    default Co1<D> a(A a, B b, C c) {
        return d -> a(a, b, c, d);
    }

    @Override
    default Co2<C, D> a(A a, B b) {
        return (c, d) -> a(a, b, c, d);
    }

    @Override
    default Co3<B, C, D> a(A a) {
        return (b, c, d) -> a(a, b, c, d);
    }

    @Override
    default Co4<A, B, C, D> a() {
        return this;
    }

    @Override
    default Co2<C, D> a(T2<? extends A, ? extends B> t) {
        return a(t.a, t.b);
    }

    @Override
    default Co3<B, C, D> a(T1<? extends A> t) {
        return a(t.a);
    }

    @Override
    default Co4<A, B, C, D> a(T0 __) {
        return a();
    }
}
