package control.functions.consumers;

import control.functions.Fn4;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-22 14:29)
 */
@FunctionalInterface
public interface Co5<A, B, C, D, E> extends Fn4<A, B, C, D, Co1<E>> {
    void uAccept(A a, B b, C c, D d, E e) throws Throwable;

    @Override
    default Co1<E> uApply(A a, B b, C c, D d) throws Throwable {
        return e -> uAccept(a, b, c, d, e);
    }
}
