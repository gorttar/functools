package control.functions;

import data.tuple.T0;
import data.tuple.T2;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 17:18)
 */
@FunctionalInterface
public interface Fn2<A, B, Rt> extends Fn1<A, Fn1<B, Rt>> {
    Rt uApply(A a, B b) throws Throwable;

    @Override
    default Fn1<B, Rt> uApply(A a) throws Throwable {
        return b -> uApply(a, b);
    }

    default Rt a(A a, B b) {
        return Sup.a(() -> uApply(a, b));
    }

    @Override
    default Fn2<A, B, Rt> a() {
        return this;
    }

    default Rt a(T2<? extends A, ? extends B> t) {
        return a(t.a, t.b);
    }

    @Override
    default Fn2<A, B, Rt> a(T0 __) {
        return a();
    }
}
