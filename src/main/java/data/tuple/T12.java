package data.tuple;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * this file is auto generated with {@link Generator} and should not be edited manually
 *
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-29)
 */
public class T12<
        A, B, C, D, E, F, G, H, I, J,
        K, L> extends
        T11<
                A, B, C, D, E, F, G, H, I, J,
                K> {
    public final L l;

    T12(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j,
        K k, L l) {
        super(
                a, b, c, d, e, f, g, h, i, j,
                k);
        this.l = l;
    }

    @Override
    public String toString() {
        return "t(" +
                Stream.of(
                        a, b, c, d, e, f, g, h, i, j,
                        k, l)
                        .map(v -> v instanceof String ? "\"" + v + "\"" : v.toString())
                        .collect(Collectors.joining(", ")) +
                ")";
    }

    @Override
    public boolean equals(Object o) {
        return this == o ||
                (
                        o instanceof T12 &&
                                super.equals(o) &&
                                Objects.equals(l, ((T12<
                                        ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,
                                        ?, ?>) o).l));
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), l);
    }
}