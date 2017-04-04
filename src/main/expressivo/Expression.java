package expressivo;

import lib6005.parser.GrammarCompiler;
import lib6005.parser.ParseTree;
import lib6005.parser.Parser;
import lib6005.parser.UnableToParseException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * An immutable data type representing a polynomial expression of:
 * + and *
 * nonnegative integers and floating-point numbers
 * variables (case-sensitive nonempty strings of letters)
 * <p>
 * <p>PS1 instructions: this is a required ADT interface.
 * You MUST NOT change its name or package or the names or type signatures of existing methods.
 * You may, however, add additional methods, or strengthen the specs of existing methods.
 * Declare concrete variants of Expression in their own Java source files.
 */
public interface Expression {
    Expression ZERO = constant(0);
    Expression ONE = constant(1);
    // Datatype definition
    //   Expression =
    //      Plus(left: Expression, right: Expression) +
    //      Multiply(left: Expression, right: Expression) +
    //      Const(value: Double) +
    //      Var(name: String)

    /**
     * Parse an expression.
     *
     * @param input expression to parse, as defined in the PS1 handout.
     * @return expression AST for the input
     * @throws IllegalArgumentException if the expression is invalid
     */
    static Expression parse(String input) {
        final ParseTree<ExpressionGrammar> parseTree;
        try {
            parseTree = Helpers.PARSER.parse(input);
        } catch (UnableToParseException e) {
            throw new IllegalArgumentException(e);
        }
        return Helpers.buildAST(parseTree);
    }

    /**
     * @return a parsable representation of this expression, such that
     * for all e:Expression, e.equals(Expression.parse(e.toString())).
     */
    @Override
    String toString();

    /**
     * @param thatObject any object
     * @return true if and only if this and thatObject are structurally-equal
     * Expressions, as defined in the PS1 handout.
     */
    @Override
    boolean equals(Object thatObject);

    /**
     * @return hash code value consistent with the equals() definition of structural
     * equality, such that for all e1,e2:Expression,
     * e1.equals(e2) implies e1.hashCode() == e2.hashCode()
     */
    @Override
    int hashCode();

    // my part

    String asSubExpression();

    Expression differentiate(String varName);

    Expression simplify(Map<String, Double> env);

    static Expression plus(Expression left, Expression right) {
        return Operation.plus(left, right);
    }

    static Expression multiply(Expression left, Expression right) {
        return Operation.multiply(left, right);
    }

    static Expression constant(double value) {
        return Primitive.constant(value);
    }

    static Expression var(String name) {
        return Primitive.var(name);
    }

    default boolean isConstant() {
        return false;
    }

    default double getValue() {
        throw new UnsupportedOperationException(String.format("Expression %s isn't constant", this));
    }

    /* Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires permission of course staff.
     */
}

class Helpers {
    static final Parser<ExpressionGrammar> PARSER;

    static {
        try {
            PARSER = GrammarCompiler
                    .compile(new File("src/expressivo/Expression.g"), ExpressionGrammar.EXPR);
        } catch (UnableToParseException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    static Expression buildAST(ParseTree<ExpressionGrammar> p) {
        switch (p.getName()) {
            case EXPR:
                final List<ParseTree<ExpressionGrammar>> children = p
                        .children()
                        .stream()
                        .filter(child -> child.getName() != ExpressionGrammar.WHITESPACE)
                        .collect(Collectors.toList());
                if (children.size() != 1) {
                    throw new RuntimeException(String.format(
                            "Expression %s should have one child only but found %d",
                            p,
                            children.size()));
                }
                return buildAST(children.get(0));
            case PLUS:
                return buildOp(p, Expression::plus, "There are no terms to sum in " + p);
            case MULTIPLY:
                return buildOp(p, Expression::multiply, "There are no factors to multiply in " + p);
            case CONSTANT:
                requireTerminal(p);
                return Expression.constant(Double.valueOf(p.getContents()));
            case VAR:
                requireTerminal(p);
                return Expression.var(p.getContents());
            case WHITESPACE:
                throw new RuntimeException("You should never reach here:" + p);
        }
        throw new RuntimeException("You should never reach here:" + p);
    }

    private static Expression buildOp(ParseTree<ExpressionGrammar> p, BinaryOperator<Expression> opProducer, String message1) {
        return p
                .children()
                .stream()
                .filter(child -> child.getName() != ExpressionGrammar.WHITESPACE)
                .map(Helpers::buildAST)
                .reduce(opProducer)
                .orElseThrow(() -> new RuntimeException(message1));
    }

    private static void requireTerminal(ParseTree<ExpressionGrammar> p) {
        if (!p.isTerminal()) {
            throw new RuntimeException(String.format("Expected terminal but %s is not terminal", p));
        }
    }
}