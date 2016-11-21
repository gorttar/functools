package control.functions;

import data.tuple.T0;

import java.util.function.Consumer;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 19:22)
 */
@FunctionalInterface
public interface Co1<A> extends Consumer<A>, Fn1<A, Void> {
    @Override
    default Void apply(A a) {
        accept(a);
        return null;
    }

    @Override
    default Co1<A> a() {
        return this;
    }

    @Override
    default Co1<A> a(T0 __) {
        return a();
    }
}
