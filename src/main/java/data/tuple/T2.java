/*
 * Copyright (c) 2008-2016 Maxifier Ltd. All Rights Reserved.
 */
package data.tuple;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Andrey Antipov (andrey.antipov@maxifier.com) (2016-10-29)
 */
public class T2<A, B> {
    public final A a;
    public final B b;

    T2(A a, B b) {
        this.a = a;
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
                        o != null &&
                                getClass() == o.getClass() &&
                                Objects.equals(a, ((T2<?, ?>) o).a) &&
                                Objects.equals(b, ((T2<?, ?>) o).b));
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }
}