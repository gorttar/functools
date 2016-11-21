package control.functions;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-21 21:50)
 */
@FunctionalInterface
public interface Co4<A, B, C, D> extends Fn1<A, Co3<B, C, D>> {
    void a(A a, B b, C c, D d);

    @Override
    default Co3<B, C, D> apply(A a) {
        return a(a);
    }

    @Override
    default Co3<B, C, D> a(A a) {
        return (b, c, d) -> a(a, b, c, d);
    }
}
