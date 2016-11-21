package data.tuple;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * this file is auto generated with {@link Generator} and should not be edited manually
 *
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-29)
 */
public class T6<A, B, C, D, E, F> extends
        T5<A, B, C, D, E> {
    public final F f;

    T6(A a, B b, C c, D d, E e, F f) {
        super(a, b, c, d, e);
        this.f = f;
    }

    @Override
    public String toString() {
        return "t(" +
                Stream.of(a, b, c, d, e, f)
                        .map(v -> v instanceof String ? "\"" + v + "\"" : v.toString())
                        .collect(Collectors.joining(", ")) +
                ")";
    }

    @Override
    public boolean equals(Object o) {
        return this == o ||
                (
                        o instanceof T6 &&
                                super.equals(o) &&
                                Objects.equals(f, ((T6<?, ?, ?, ?, ?, ?>) o).f));
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), f);
    }
}