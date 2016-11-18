package control.functions;

import java.util.function.Consumer;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 19:22)
 */
@FunctionalInterface
public interface Co<A> extends Consumer<A> {
    void a(A a);

    @Override
    default void accept(A a) {
        a(a);
    }
}
