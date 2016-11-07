package data.tuple;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * this file is auto generated with {@link Generator} and should not be edited manually
 *
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-29)
 */
public class T14<
        A, B, C, D, E, F, G, H, I, J,
        K, L, M, N> extends
        T13<
                A, B, C, D, E, F, G, H, I, J,
                K, L, M> {
    public final N n;

    T14(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j,
        K k, L l, M m, N n) {
        super(
                a, b, c, d, e, f, g, h, i, j,
                k, l, m);
        this.n = n;
    }

    @Override
    public String toString() {
        return "t(" +
                Stream.of(
                        a, b, c, d, e, f, g, h, i, j,
                        k, l, m, n)
                        .map(v -> v instanceof String ? "\"" + v + "\"" : v.toString())
                        .collect(Collectors.joining(", ")) +
                ")";
    }

    @Override
    public boolean equals(Object o) {
        return this == o ||
                (
                        o instanceof T14 &&
                                super.equals(o) &&
                                Objects.equals(n, ((T14<
                                        ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,
                                        ?, ?, ?, ?>) o).n));
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), n);
    }
}