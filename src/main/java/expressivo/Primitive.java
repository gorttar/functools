/*
 * Copyright (c) 2017 Andrey Antipov. All Rights Reserved.
 */
package expressivo;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2017-04-02)
 */
abstract class Primitive<T> implements Expression {

    final T value;

    Primitive(T value) {
        this.value = value;
    }

    @Override
    public String asSubExpression() {
        return toString();
    }

    @Override
    public String toString() {
        return "" + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Primitive)) return false;
        Primitive<?> primitive = (Primitive<?>) o;
        return Objects.equals(value, primitive.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    static Primitive constant(double value) {
        return new Const(value);
    }

    static Primitive var(String name) {
        return new Var(name);
    }

    private static class Const extends Primitive<Double> {
        private Const(Double value) {
            super(value);
        }

        @Override
        public String toString() {
            final String preVal = String.format(Locale.US, "%.6f", this.value)
                    .replaceAll("\\.0+$", "");
            return preVal.contains(".") ? preVal.replaceAll("0+$", "") : preVal;
        }

        @Override
        public Expression differentiate(String varName) {
            return ZERO;
        }

        @Override
        public Expression simplify(Map<String, Double> env) {
            return this;
        }

        @Override
        public boolean isConstant() {
            return true;
        }

        @Override
        public double getValue() {
            return value;
        }
    }

    private static class Var extends Primitive<String> {
        private Var(String value) {
            super(value);
        }

        @Override
        public Expression differentiate(String varName) {
            return value.equals(varName) ? ONE : ZERO;
        }

        @Override
        public Expression simplify(Map<String, Double> env) {
            final Expression result;
            if (env.containsKey(value)) {
                result = constant(env.get(value));
            } else {
                result = this;
            }
            return result;
        }
    }

}
