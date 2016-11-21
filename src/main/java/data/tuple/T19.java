package data.tuple;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * this file is auto generated with {@link Generator} and should not be edited manually
 *
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-29)
 */
public class T19<
        A, B, C, D, E, F, G, H, I, J,
        K, L, M, N, O, P, Q, R, S> extends
        T18<
                A, B, C, D, E, F, G, H, I, J,
                K, L, M, N, O, P, Q, R> {
    public final S s;

    T19(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j,
        K k, L l, M m, N n, O o, P p, Q q, R r, S s) {
        super(
                a, b, c, d, e, f, g, h, i, j,
                k, l, m, n, o, p, q, r);
        this.s = s;
    }

    @Override
    public String toString() {
        return "t(" +
                Stream.of(
                        a, b, c, d, e, f, g, h, i, j,
                        k, l, m, n, o, p, q, r, s)
                        .map(v -> v instanceof String ? "\"" + v + "\"" : v.toString())
                        .collect(Collectors.joining(", ")) +
                ")";
    }

    @Override
    public boolean equals(Object o) {
        return this == o ||
                (
                        o instanceof T19 &&
                                super.equals(o) &&
                                Objects.equals(s, ((T19<
                                        ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,
                                        ?, ?, ?, ?, ?, ?, ?, ?, ?>) o).s));
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), s);
    }
}