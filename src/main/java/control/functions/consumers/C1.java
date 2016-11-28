package control.functions.consumers;

import control.functions.F1;
import data.tuple.T0;

import java.util.function.Consumer;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 19:22)
 */
@FunctionalInterface
public interface C1<A> extends Consumer<A>, F1<A, Void> {
    void uAccept(A a) throws Throwable;

    @Override
    default void accept(A a) {
        a(a);
    }

    @Override
    default Void uApply(A a) throws Throwable {
        uAccept(a);
        return null;
    }

    @Override
    default C1<A> a() {
        return this;
    }

    @Override
    default C1<A> a(T0 __) {
        return a();
    }
}
