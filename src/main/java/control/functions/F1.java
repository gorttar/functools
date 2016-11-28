package control.functions;

import data.tuple.T0;
import data.tuple.T1;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 17:17)
 */
@FunctionalInterface
public interface F1<A, Rt> extends Function<A, Rt> {
    Rt uApply(A a) throws Throwable;

    default Rt a(A a) {
        return Sr.a(() -> uApply(a));
    }

    @Override
    default Rt apply(A a) {
        return a(a);
    }

    default Rt a(T1<? extends A> t) {
        return apply(t.a);
    }

    default F1<A, Rt> a() {
        return this;
    }

    default F1<A, Rt> a(T0 __) {
        return a();
    }

    default <B> F1<B, Rt> c(Function<? super B, ? extends A> before) {
        Objects.requireNonNull(before);
        return b -> a(before.apply(b));
    }

    default <Rt2> F1<A, Rt2> ltr(Function<? super Rt, ? extends Rt2> after) {
        return a -> after.apply(a(a));
    }

    static <A> F1<A, A> id() {
        return a -> a;
    }

}
