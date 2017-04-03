/*
 * Copyright (c) 2017 Andrey Antipov. All Rights Reserved.
 */
package expressivo;

import static expressivo.Expression.constant;

import java.util.Map;
import java.util.Objects;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2017-04-02)
 */
abstract class Operation implements Expression {
    final Expression left;
    final Expression right;

    Operation(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o.getClass().equals(getClass()))) return false;
        Operation operation = (Operation) o;
        return Objects.equals(toString(), operation.toString()) &&
                Objects.equals(right, operation.right);
    }

    static Operation plus(Expression left, Expression right) {
        return new Plus(left, right);
    }

    static Operation multiply(Expression left, Expression right) {
        return new Multiply(left, right);
    }

    private static class Plus extends Operation {
        private Plus(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public String toString() {
            return "" + left + "+" + right;
        }


        @Override
        public String asSubExpression() {
            return "(" + toString() + ')';
        }

        @Override
        public Expression differentiate(String varName) {
            return plus(left.differentiate(varName), right.differentiate(varName));
        }

        @Override
        public Expression simplify(Map<String, Double> env) {
            final Expression result;
            final Expression left = this.left.simplify(env);
            final Expression right = this.right.simplify(env);
            if (left.isConstant() && right.isConstant()) {
                result = constant(left.getValue() + right.getValue());
            } else if (left.equals(ZERO)) {
                result = right;
            } else if (right.equals(ZERO)) {
                result = left;
            } else {
                result = plus(left, right);
            }
            return result;
        }
    }

    private static class Multiply extends Operation {
        private Multiply(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public String toString() {
            return left.asSubExpression() + "*" + right.asSubExpression();
        }

        @Override
        public String asSubExpression() {
            return toString();
        }

        @Override
        public Expression differentiate(String varName) {
            return plus(
                    multiply(left, right.differentiate(varName)),
                    multiply(left.differentiate(varName), right));
        }

        @Override
        public Expression simplify(Map<String, Double> env) {
            final Expression result;
            final Expression left = this.left.simplify(env);
            final Expression right = this.right.simplify(env);
            if (left.equals(ZERO) || right.equals(ZERO)) {
                result = ZERO;
            } else if (left.isConstant() && right.isConstant()) {
                result = constant(left.getValue() * right.getValue());
            } else if (left.equals(ONE)) {
                result = right;
            } else if (right.equals(ONE)) {
                result = left;
            } else {
                result = multiply(left, right);
            }
            return result;
        }
    }
}
