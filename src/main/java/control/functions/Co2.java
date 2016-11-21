package control.functions;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 19:24)
 */
@FunctionalInterface
public interface Co2<A, B> extends Fn1<A, Co1<B>> {
    void a(A a, B b);

    @Override
    default Co1<B> apply(A a) {
        return a(a);
    }

    @Override
    default Co1<B> a(A a) {
        return b -> a(a, b);
    }
}
