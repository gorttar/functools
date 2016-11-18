package control.functions;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 19:02)
 */
@FunctionalInterface
public interface Fn3<A, B, C, Rt> extends Fn1<A, Fn2<B, C, Rt>> {
    Rt a(A a, B b, C c);

    default Fn1<C, Rt> a(A a, B b) {
        return c -> a(a, b, c);
    }

    @Override
    default Fn2<B, C, Rt> a(A a) {
        return (b, c) -> a(a, b, c);
    }
}
