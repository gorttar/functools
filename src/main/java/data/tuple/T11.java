package data.tuple;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * this file is auto generated with {@link Generator} and should not be edited manually
 *
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-29)
 */
public class T11<
        A, B, C, D, E, F, G, H, I, J,
        K> extends
        T10<A, B, C, D, E, F, G, H, I, J> {
    public final K k;

    T11(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j,
        K k) {
        super(a, b, c, d, e, f, g, h, i, j);
        this.k = k;
    }

    @Override
    public String toString() {
        return "t(" +
                Stream.of(
                        a, b, c, d, e, f, g, h, i, j,
                        k)
                        .map(v -> v instanceof String ? "\"" + v + "\"" : v.toString())
                        .collect(Collectors.joining(", ")) +
                ")";
    }

    @Override
    public boolean equals(Object o) {
        return this == o ||
                (
                        o instanceof T11 &&
                                super.equals(o) &&
                                Objects.equals(k, ((T11<
                                        ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,
                                        ?>) o).k));
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), k);
    }
}