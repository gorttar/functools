package data.tuple;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * this file is auto generated with {@link Generator} and should not be edited manually
 *
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-29)
 */
public class T7<A, B, C, D, E, F, G> extends
        T6<A, B, C, D, E, F> {
    public final G g;

    T7(A a, B b, C c, D d, E e, F f, G g) {
        super(a, b, c, d, e, f);
        this.g = g;
    }

    @Override
    public String toString() {
        return "t(" +
                Stream.of(a, b, c, d, e, f, g)
                        .map(v -> v instanceof String ? "\"" + v + "\"" : v.toString())
                        .collect(Collectors.joining(", ")) +
                ")";
    }

    @Override
    public boolean equals(Object o) {
        return this == o ||
                (
                        o instanceof T7 &&
                                super.equals(o) &&
                                Objects.equals(g, ((T7<?, ?, ?, ?, ?, ?, ?>) o).g));
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), g);
    }
}