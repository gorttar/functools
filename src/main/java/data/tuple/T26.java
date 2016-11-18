package data.tuple;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * this file is auto generated with {@link Generator} and should not be edited manually
 *
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-29)
 */
public class T26<
        A, B, C, D, E, F, G, H, I, J,
        K, L, M, N, O, P, Q, R, S, T,
        U, V, W, X, Y, Z> extends
        T25<
                A, B, C, D, E, F, G, H, I, J,
                K, L, M, N, O, P, Q, R, S, T,
                U, V, W, X, Y> {
    public final Z z;

    T26(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j,
        K k, L l, M m, N n, O o, P p, Q q, R r, S s, T t,
        U u, V v, W w, X x, Y y, Z z) {
        super(
                a, b, c, d, e, f, g, h, i, j,
                k, l, m, n, o, p, q, r, s, t,
                u, v, w, x, y);
        this.z = z;
    }

    @Override
    public String toString() {
        return "t(" +
                Stream.of(
                        a, b, c, d, e, f, g, h, i, j,
                        k, l, m, n, o, p, q, r, s, t,
                        u, v, w, x, y, z)
                        .map(v -> v instanceof String ? "\"" + v + "\"" : v.toString())
                        .collect(Collectors.joining(", ")) +
                ")";
    }

    @Override
    public boolean equals(Object o) {
        return this == o ||
                (
                        o instanceof T26 &&
                                super.equals(o) &&
                                Objects.equals(z, ((T26<
                                        ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,
                                        ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,
                                        ?, ?, ?, ?, ?, ?>) o).z));
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), z);
    }
}