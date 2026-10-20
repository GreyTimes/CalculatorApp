package com.example.calculatorapp;

public class CalculatorLogic {

    public double add(double a, double b) {
        // TODO:Implement addition logic here
        return 0.0;
    }

    public double subtract(double a, double b) {
        // TODO: Implement subtraction logic here
        return 0.0;
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
}