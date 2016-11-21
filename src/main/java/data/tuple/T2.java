package data.tuple;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * this file is auto generated with {@link Generator} and should not be edited manually
 *
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-29)
 */
public class T2<A, B> extends
        T1<A> {
    public final B b;

    T2(A a, B b) {
        super(a);
        this.b = b;
    }

    @Override
    public String toString() {
        return "t(" +
                Stream.of(a, b)
                        .map(v -> v instanceof String ? "\"" + v + "\"" : v.toString())
                        .collect(Collectors.joining(", ")) +
                ")";
    }

    @Override
    public boolean equals(Object o) {
        return this == o ||
                (
                        o instanceof T2 &&
                                super.equals(o) &&
                                Objects.equals(b, ((T2<?, ?>) o).b));
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), b);
    }
}