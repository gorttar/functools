package data.tuple;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * this file is auto generated with {@link Generator} and should not be edited manually
 *
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-29)
 */
public class T8<A, B, C, D, E, F, G, H> extends
        T7<A, B, C, D, E, F, G> {
    public final H h;

    T8(A a, B b, C c, D d, E e, F f, G g, H h) {
        super(a, b, c, d, e, f, g);
        this.h = h;
    }

    @Override
    public String toString() {
        return "t(" +
                Stream.of(a, b, c, d, e, f, g, h)
                        .map(v -> v instanceof String ? "\"" + v + "\"" : v.toString())
                        .collect(Collectors.joining(", ")) +
                ")";
    }

    @Override
    public boolean equals(Object o) {
        return this == o ||
                (
                        o instanceof T8 &&
                                super.equals(o) &&
                                Objects.equals(h, ((T8<?, ?, ?, ?, ?, ?, ?, ?>) o).h));
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), h);
    }
}