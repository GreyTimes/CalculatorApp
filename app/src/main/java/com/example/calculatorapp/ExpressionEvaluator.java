package com.example.calculatorapp;

import java.util.List;

public class ExpressionEvaluator {
    private final CalculatorLogic calculatorLogic = new CalculatorLogic();
    private final ScientificOperations scientificOperations = new ScientificOperations();

    private List<String> tokens;
    private int position;

    public double evaluate(List<String> tokens) {
        if (tokens == null || tokens.isEmpty()) {
            throw new IllegalArgumentException("Expression is empty");
        }

        this.tokens = tokens;
        this.position = 0;

        double result = parseExpression();

        if (position < tokens.size()) {
            throw new IllegalArgumentException("Unexpected symbol: " + tokens.get(position));
        }

        return result;
    }

    private double parseExpression() {
        double result = parseTerm();

        while (true) {
            if (match("+")) {
                result = calculatorLogic.add(result, parseTerm());
            } else if (match("-")) {
                result = calculatorLogic.subtract(result, parseTerm());
            } else {
                return result;
            }
        }
    }

    private double parseTerm() {
        double result = parseUnary();

        while (true) {
            if (match("*")) {
                result = calculatorLogic.multiply(result, parseUnary());
            } else if (match("/")) {
                result = calculatorLogic.divide(result, parseUnary());
            } else {
                return result;
            }
        }
    }

    private double parseUnary() {
        if (match("-")) {
            return calculatorLogic.subtract(0, parseUnary());
        }
        if (match("+")) {
            return parseUnary();
        }
        return parsePower();
    }

    private double parsePower() {
        double base = parsePrimary();

        if (match("^")) {
            return scientificOperations.power(base, parseUnary());
        }
        return base;
    }

    private double parsePrimary() {
        String token = next();

        if (token.equals("(")) {
            double value = parseExpression();
            expect(")");
            return value;
        }

        if (token.equals("√")) {
            return scientificOperations.squareRoot(parseUnary());
        }

        if (Character.isDigit(token.charAt(0)) || token.charAt(0) == '.') {
            try {
                return Double.parseDouble(token);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid number: " + token);
            }
        }

        String word = token.toLowerCase();

        if (word.equals("pi") || word.equals("π")) {
            return ScientificOperations.PI;
        }
        if (word.equals("e")) {
            return ScientificOperations.E;
        }

        if (isFunction(word)) {
            expect("(");
            double argument = parseExpression();
            expect(")");
            return applyFunction(word, argument);
        }

        throw new IllegalArgumentException("Unexpected symbol: " + token);
    }

    private boolean isFunction(String word) {
        return word.equals("sin") || word.equals("cos") || word.equals("tan")
                || word.equals("log") || word.equals("ln");
    }

    private double applyFunction(String function, double argument) {
        switch (function) {
            case "sin":
                return scientificOperations.sin(argument);
            case "cos":
                return scientificOperations.cos(argument);
            case "tan":
                return scientificOperations.tan(argument);
            case "ln":
                return scientificOperations.log(argument);
            case "log":
                return calculatorLogic.divide(
                        scientificOperations.log(argument),
                        scientificOperations.log(10));
            default:
                throw new IllegalArgumentException("Unknown function: " + function);
        }
    }

    private String next() {
        if (position >= tokens.size()) {
            throw new IllegalArgumentException("Expression is incomplete");
        }
        return tokens.get(position++);
    }

    private boolean match(String expected) {
        if (position < tokens.size() && tokens.get(position).equals(expected)) {
            position++;
            return true;
        }
        return false;
    }

    private void expect(String expected) {
        if (!match(expected)) {
            throw new IllegalArgumentException("Missing " + expected);
        }
    }
}
