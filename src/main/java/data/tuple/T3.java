package data.tuple;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * this file is auto generated with {@link Generator} and should not be edited manually
 *
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-29)
 */
public class T3<A, B, C> extends
        T2<A, B> {
    public final C c;

    T3(A a, B b, C c) {
        super(a, b);
        this.c = c;
    }

    @Override
    public String toString() {
        return "t(" +
                Stream.of(a, b, c)
                        .map(v -> v instanceof String ? "\"" + v + "\"" : v.toString())
                        .collect(Collectors.joining(", ")) +
                ")";
    }

    @Override
    public boolean equals(Object o) {
        return this == o ||
                (
                        o instanceof T3 &&
                                super.equals(o) &&
                                Objects.equals(c, ((T3<?, ?, ?>) o).c));
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), c);
    }
}