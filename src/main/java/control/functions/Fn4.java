package control.functions;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 19:10)
 */
@FunctionalInterface
public interface Fn4<A, B, C, D, Rt> extends Fn1<A, Fn3<B, C, D, Rt>> {
    Rt a(A a, B b, C c, D d);

    @Override
    default Fn3<B, C, D, Rt> a(A a) {
        return (b, c, d) -> a(a, b, c, d);
    }
}
