package com.example.calculatorapp;

public class CalculatorLogic {

    // Basic operations
    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return a / b;
    }

    // Scientific operations
    public double squareRoot(double a) {
        if (a < 0) {
            throw new IllegalArgumentException(
                    "Cannot calculate square root of a negative number");
        }
        return Math.sqrt(a);
    }

    public double square(double a) {
        return a * a;
    }

    public double power(double base, double exponent) {
        double result = Math.pow(base, exponent);

        if (!Double.isFinite(result)) {
            throw new IllegalArgumentException("Invalid power operation");
        }

        return result;
    }

    // Trigonometric operations use degrees
    public double sin(double degrees) {
        return Math.sin(Math.toRadians(degrees));
    }

    public double cos(double degrees) {
        return Math.cos(Math.toRadians(degrees));
    }

    public double tan(double degrees) {
        if (Math.abs(Math.cos(Math.toRadians(degrees))) < 1e-10) {
            throw new IllegalArgumentException(
                    "Tangent is undefined for this angle");
        }
        return Math.tan(Math.toRadians(degrees));
    }

    public double log(double a) {
        if (a <= 0) {
            throw new IllegalArgumentException(
                    "Logarithm requires a positive number");
        }
        return Math.log10(a);
    }

    public double ln(double a) {
        if (a <= 0) {
            throw new IllegalArgumentException(
                    "Natural logarithm requires a positive number");
        }
        return Math.log(a);
    }

    // Mathematical constants
    public double pi() {
        return Math.PI;
    }

    public double e() {
        return Math.E;
    }
}