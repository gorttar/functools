package control.functions;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 19:27)
 */
@FunctionalInterface
public interface Co3<A, B, C> extends Fn1<A, Co2<B, C>> {
    void a(A a, B b, C c);

    @Override
    default Co2<B, C> a(A a) {
        return (b, c) -> a(a, b, c);
    }
}
