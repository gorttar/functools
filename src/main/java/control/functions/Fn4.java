package control.functions;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 19:10)
 */
@FunctionalInterface
public interface Fn4<A, B, C, D, Rt> extends Fn1<A, Fn3<B, C, D, Rt>> {
    Rt a(A a, B b, C c, D d);

    default Fn1<D, Rt> a(A a, B b, C c) {
        return d -> a(a, b, c, d);
    }

    default Fn2<C, D, Rt> a(A a, B b) {
        return (c, d) -> a(a, b, c, d);
    }

    @Override
    default Fn3<B, C, D, Rt> a(A a) {
        return (b, c, d) -> a(a, b, c, d);
    }
}
