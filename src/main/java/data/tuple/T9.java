package data.tuple;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * this file is auto generated with {@link Generator} and should not be edited manually
 *
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-29)
 */
public class T9<A, B, C, D, E, F, G, H, I> extends
        T8<A, B, C, D, E, F, G, H> {
    public final I i;

    T9(A a, B b, C c, D d, E e, F f, G g, H h, I i) {
        super(a, b, c, d, e, f, g, h);
        this.i = i;
    }

    @Override
    public String toString() {
        return "t(" +
                Stream.of(a, b, c, d, e, f, g, h, i)
                        .map(v -> v instanceof String ? "\"" + v + "\"" : v.toString())
                        .collect(Collectors.joining(", ")) +
                ")";
    }

    @Override
    public boolean equals(Object o) {
        return this == o ||
                (
                        o instanceof T9 &&
                                super.equals(o) &&
                                Objects.equals(i, ((T9<?, ?, ?, ?, ?, ?, ?, ?, ?>) o).i));
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), i);
    }
}