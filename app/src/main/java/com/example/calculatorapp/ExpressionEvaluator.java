package com.example.calculatorapp;

public class ExpressionEvaluator {

    private String input;
    private int position;

    public double evaluate(String expression) {
        input = expression.replace(" ", "");
        position = 0;

        if (input.isEmpty()) {
            throw new IllegalArgumentException("Enter an expression");
        }

        double result = parseExpression();

        if (position < input.length()) {
            throw new IllegalArgumentException("Invalid expression");
        }

        if (!Double.isFinite(result)) {
            throw new IllegalArgumentException("Invalid result");
        }

        return result;
    }

    // Addition and subtraction
    private double parseExpression() {
        double value = parseTerm();

        while (position < input.length()) {
            char operator = input.charAt(position);

            if (operator != '+' && operator != '-') {
                break;
            }

            position++;
            double next = parseTerm();

            if (operator == '+') {
                value += next;
            } else {
                value -= next;
            }
        }

        return value;
    }

    // Multiplication and division
    private double parseTerm() {
        double value = parseUnary();

        while (position < input.length()) {
            char operator = input.charAt(position);

            if (operator != '*' && operator != '/') {
                break;
            }

            position++;
            double next = parseUnary();

            if (operator == '*') {
                value *= next;
            } else {
                if (next == 0) {
                    throw new IllegalArgumentException(
                            "Cannot divide by zero");
                }

                value /= next;
            }
        }

        return value;
    }

    // Positive and negative numbers
    private double parseUnary() {
        if (match('+')) {
            return parseUnary();
        }

        if (match('-')) {
            return -parseUnary();
        }

        return parsePower();
    }

    // Exponentiation
    private double parsePower() {
        double base = parsePrimary();

        if (match('^')) {
            double exponent = parseUnary();
            base = Math.pow(base, exponent);
        }

        if (!Double.isFinite(base)) {
            throw new IllegalArgumentException("Invalid result");
        }

        return base;
    }

    // Numbers, parentheses, functions and constants
    private double parsePrimary() {
        if (match('(')) {
            double value = parseExpression();

            if (!match(')')) {
                throw new IllegalArgumentException(
                        "Missing closing parenthesis");
            }

            return value;
        }

        if (position < input.length()
                && Character.isLetter(input.charAt(position))) {
            return parseFunctionOrConstant();
        }

        return parseNumber();
    }

    private double parseFunctionOrConstant() {
        int start = position;

        while (position < input.length()
                && Character.isLetter(input.charAt(position))) {
            position++;
        }

        String name = input.substring(start, position).toLowerCase();

        // Mathematical constants
        if (name.equals("pi")) {
            return Math.PI;
        }

        if (name.equals("e")) {
            return Math.E;
        }

        // Scientific functions require parentheses
        if (!match('(')) {
            throw new IllegalArgumentException(
                    "Function requires parentheses");
        }

        double argument = parseExpression();

        if (!match(')')) {
            throw new IllegalArgumentException(
                    "Missing closing parenthesis");
        }

        double result;

        switch (name) {
            case "sin":
                result = Math.sin(Math.toRadians(argument));
                break;

            case "cos":
                result = Math.cos(Math.toRadians(argument));
                break;

            case "tan":
                if (Math.abs(
                        Math.cos(Math.toRadians(argument))) < 1e-10) {
                    throw new IllegalArgumentException(
                            "Tangent is undefined for this angle");
                }

                result = Math.tan(Math.toRadians(argument));
                break;

            case "sqrt":
                if (argument < 0) {
                    throw new IllegalArgumentException(
                            "Cannot calculate square root of a negative number");
                }

                result = Math.sqrt(argument);
                break;

            case "square":
                result = argument * argument;
                break;

            case "log":
                if (argument <= 0) {
                    throw new IllegalArgumentException(
                            "Logarithm requires a positive number");
                }

                result = Math.log10(argument);
                break;

            case "ln":
                if (argument <= 0) {
                    throw new IllegalArgumentException(
                            "Natural logarithm requires a positive number");
                }

                result = Math.log(argument);
                break;

            default:
                throw new IllegalArgumentException(
                        "Unknown function: " + name);
        }

        if (!Double.isFinite(result)) {
            throw new IllegalArgumentException("Invalid result");
        }

        return result;
    }

    // Read a decimal number
    private double parseNumber() {
        int start = position;
        boolean hasDigit = false;

        while (position < input.length()
                && Character.isDigit(input.charAt(position))) {
            position++;
            hasDigit = true;
        }

        if (position < input.length()
                && input.charAt(position) == '.') {
            position++;

            while (position < input.length()
                    && Character.isDigit(input.charAt(position))) {
                position++;
                hasDigit = true;
            }
        }

        if (!hasDigit) {
            throw new IllegalArgumentException(
                    "Expected a number");
        }

        return Double.parseDouble(input.substring(start, position));
    }

    // Consume a character if it matches
    private boolean match(char expected) {
        if (position < input.length()
                && input.charAt(position) == expected) {
            position++;
            return true;
        }

        return false;
    }
}