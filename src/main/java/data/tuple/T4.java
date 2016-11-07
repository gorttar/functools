package data.tuple;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * this file is auto generated with {@link Generator} and should not be edited manually
 *
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-29)
 */
public class T4<A, B, C, D> extends
        T3<A, B, C> {
    public final D d;

    T4(A a, B b, C c, D d) {
        super(a, b, c);
        this.d = d;
    }

    @Override
    public String toString() {
        return "t(" +
                Stream.of(a, b, c, d)
                        .map(v -> v instanceof String ? "\"" + v + "\"" : v.toString())
                        .collect(Collectors.joining(", ")) +
                ")";
    }

    @Override
    public boolean equals(Object o) {
        return this == o ||
                (
                        o instanceof T4 &&
                                super.equals(o) &&
                                Objects.equals(d, ((T4<?, ?, ?, ?>) o).d));
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), d);
    }
}