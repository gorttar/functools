package data.tuple;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * this file is auto generated with {@link Generator} and should not be edited manually
 *
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-29)
 */
public class T21<
        A, B, C, D, E, F, G, H, I, J,
        K, L, M, N, O, P, Q, R, S, T,
        U> extends
        T20<
                A, B, C, D, E, F, G, H, I, J,
                K, L, M, N, O, P, Q, R, S, T> {
    public final U u;

    T21(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j,
        K k, L l, M m, N n, O o, P p, Q q, R r, S s, T t,
        U u) {
        super(
                a, b, c, d, e, f, g, h, i, j,
                k, l, m, n, o, p, q, r, s, t);
        this.u = u;
    }

    @Override
    public String toString() {
        return "t(" +
                Stream.of(
                        a, b, c, d, e, f, g, h, i, j,
                        k, l, m, n, o, p, q, r, s, t,
                        u)
                        .map(v -> v instanceof String ? "\"" + v + "\"" : v.toString())
                        .collect(Collectors.joining(", ")) +
                ")";
    }

    @Override
    public boolean equals(Object o) {
        return this == o ||
                (
                        o instanceof T21 &&
                                super.equals(o) &&
                                Objects.equals(u, ((T21<
                                        ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,
                                        ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,
                                        ?>) o).u));
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), u);
    }
}