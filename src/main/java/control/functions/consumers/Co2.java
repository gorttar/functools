package control.functions.consumers;

import control.functions.Fn1;
import data.tuple.T0;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 19:24)
 */
@FunctionalInterface
public interface Co2<A, B> extends Fn1<A, Co1<B>> {
    void uAccept(A a, B b) throws Throwable;

    default Void a(A a, B b) {
        return a(a).a(b);
    }

    @Override
    default Co1<B> uApply(A a) throws Throwable {
        return b -> uAccept(a, b);
    }

    @Override
    default Co2<A, B> a() {
        return this;
    }

    @Override
    default Co2<A, B> a(T0 __) {
        return a();
    }
}
