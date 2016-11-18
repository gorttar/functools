package control.functions;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 19:24)
 */
@FunctionalInterface
public interface Co2<A, B> extends Fn1<A, Co<B>> {
    void a(A a, B b);

    @Override
    default Co<B> a(A a) {
        return b -> a(a, b);
    }
}
