package control.functions;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 17:18)
 */
@FunctionalInterface
public interface Fn2<A, B, Rt> extends Fn1<A, Fn1<B, Rt>> {
    Rt a(A a, B b);

    @Override
    default Fn1<B, Rt> a(A a) {
        return b -> a(a, b);
    }
}
