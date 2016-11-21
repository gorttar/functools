package data.tuple;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * this file is auto generated with {@link Generator} and should not be edited manually
 *
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-29)
 */
public class T1<A> extends
        T0 {
    public final A a;

    T1(A a) {
        super();
        this.a = a;
    }

    @Override
    public String toString() {
        return "t(" +
                Stream.of(a)
                        .map(v -> v instanceof String ? "\"" + v + "\"" : v.toString())
                        .collect(Collectors.joining(", ")) +
                ")";
    }

    @Override
    public boolean equals(Object o) {
        return this == o ||
                (
                        o instanceof T1 &&
                                super.equals(o) &&
                                Objects.equals(a, ((T1<?>) o).a));
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), a);
    }
}