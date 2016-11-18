package data.tuple;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * this file is auto generated with {@link Generator} and should not be edited manually
 *
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-29)
 */
public class T5<A, B, C, D, E> extends
        T4<A, B, C, D> {
    public final E e;

    T5(A a, B b, C c, D d, E e) {
        super(a, b, c, d);
        this.e = e;
    }

    @Override
    public String toString() {
        return "t(" +
                Stream.of(a, b, c, d, e)
                        .map(v -> v instanceof String ? "\"" + v + "\"" : v.toString())
                        .collect(Collectors.joining(", ")) +
                ")";
    }

    @Override
    public boolean equals(Object o) {
        return this == o ||
                (
                        o instanceof T5 &&
                                super.equals(o) &&
                                Objects.equals(e, ((T5<?, ?, ?, ?, ?>) o).e));
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), e);
    }
}