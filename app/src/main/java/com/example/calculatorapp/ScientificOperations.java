package com.example.calculatorapp;

public class ScientificOperations {
    public static final double PI = Math.PI;
    public static final double E = Math.E;

    public double squareRoot(double a) {
        if (a < 0) {
            throw new IllegalArgumentException("Cannot calculate square root of a negative number");
        }
        return Math.sqrt(a);
    }

    public double power(double base, double exp) {
        return Math.pow(base, exp);
    }

    public double sin(double degrees) {
        return Math.sin(Math.toRadians(degrees));
    }

    public double cos(double degrees) {
        return Math.cos(Math.toRadians(degrees));
    }

    public double tan(double degrees) {
        if (Math.abs(degrees % 180) == 90) {
            throw new IllegalArgumentException("Tangent is undefined for this angle");
        }
        return Math.tan(Math.toRadians(degrees));
    }

    public double log(double a) {
        if (a <= 0) {
            throw new IllegalArgumentException("Logarithm undefined for non-positive numbers");
        }
        return Math.log(a);
}}
