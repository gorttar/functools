package control.functions.consumers;

import control.functions.Fn3;
import data.tuple.T0;
import data.tuple.T1;
import data.tuple.T2;
import data.tuple.T4;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-21 21:50)
 */
@FunctionalInterface
public interface Co4<A, B, C, D> extends Fn3<A, B, C, Co1<D>> {
    void uAccept(A a, B b, C c, D d) throws Throwable;

    @Override
    default Co1<D> uApply(A a, B b, C c) throws Throwable {
        return d -> uAccept(a, b, c, d);
    }

    default Void a(A a, B b, C c, D d) {
        return a(a, b, c).a(d);
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

    default Void a(T4<? extends A, ? extends B, ? extends C, ? extends D> t) {
        return a(t.a, t.b, t.c, t.d);
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
