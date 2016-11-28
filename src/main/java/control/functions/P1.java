package control.functions;

import java.util.function.Predicate;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-23 14:14)
 */
@FunctionalInterface
public interface P1<A> extends Predicate<A>, F1<A, Boolean> {
    boolean uTest(A a) throws Throwable;

    @Override
    default boolean test(A a) {
        return Sr.a(() -> uTest(a));
    }

    @Override
    default Boolean uApply(A a) throws Throwable {
        return uTest(a);
    }
}
