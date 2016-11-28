package control.functions;

import data.tuple.T0;

import java.util.function.BiPredicate;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-23 15:02)
 */
@FunctionalInterface
public interface P2<A, B> extends BiPredicate<A, B>, F1<A, P1<B>> {
    boolean uTest(A a, B b) throws Throwable;

    @Override
    default boolean test(A a, B b) {
        return Sr.a(() -> uTest(a, b));
    }

    @Override
    default P1<B> uApply(A a) throws Throwable {
        return b -> uTest(a, b);
    }

    default Boolean a(A a, B b) {
        return a(a).a(b);
    }

    @Override
    default P2<A, B> a() {
        return this;
    }

    @Override
    default P2<A, B> a(T0 __) {
        return a();
    }

    default P2<B, A> fp12() {
        return (b, a) -> uTest(a, b);
    }
}
