package com.example.calculatorapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView resultText;
    private CalculatorLogic calculatorLogic;

    private double firstNumber;
    private String pendingOperation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link UI components
        editText = findViewById(R.id.editText2);
        resultText = findViewById(R.id.resultText);

        // Initialize logic handler
        calculatorLogic = new CalculatorLogic();

        // Wire up calculator buttons to display and logic
        int[] numberButtonIds = {
                R.id.num1,
                R.id.num2,
                R.id.num3,
                R.id.num4,
                R.id.num5,
                R.id.num6,
                R.id.num7,
                R.id.num8,
                R.id.num9,
                R.id.zero
        };
        for (int id : numberButtonIds) {
            Button button = findViewById(id);

            button.setOnClickListener(v -> {
                String currentText = editText.getText().toString();
                String number = button.getText().toString();

                editText.setText(currentText + number);
            });
        }
        // Decimal point
        Button dotButton = findViewById(R.id.dot);

        dotButton.setOnClickListener(v -> {
            String currentText = editText.getText().toString();

            if (!currentText.contains(".")) {
                if (currentText.isEmpty()) {
                    editText.setText("0.");
                } else {
                    editText.setText(currentText + ".");
                }
            }
        });

        // Addition
        Button addButton = findViewById(R.id.add);
        addButton.setOnClickListener(v -> selectOperation("+"));

        // Subtraction
        Button subButton = findViewById(R.id.sub);
        subButton.setOnClickListener(v -> selectOperation("-"));

        // Multiplication
        Button mulButton = findViewById(R.id.mul);
        mulButton.setOnClickListener(v -> selectOperation("*"));

        // Division
        Button divButton = findViewById(R.id.div);
        divButton.setOnClickListener(v -> selectOperation("/"));

        // Clear everything
        Button clearButton = findViewById(R.id.clear_text);

        clearButton.setOnClickListener(v -> {
            editText.setText("");
            resultText.setText("0");
            pendingOperation = "";
            firstNumber = 0;
        });

        // Submit calculation
        Button submitButton = findViewById(R.id.submit);

        submitButton.setOnClickListener(v -> calculateResult());
    }

    private void selectOperation(String operation) {
        String currentText = editText.getText().toString();

        if (currentText.isEmpty()) {
            Toast.makeText(this,
                    "Enter the first number",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            firstNumber = Double.parseDouble(currentText);
            pendingOperation = operation;

            editText.setText("");

        } catch (NumberFormatException e) {
            Toast.makeText(this,
                    "Invalid number",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void calculateResult() {
        String currentText = editText.getText().toString();

        if (pendingOperation.isEmpty()) {
            Toast.makeText(this,
                    "Select an operation first",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentText.isEmpty()) {
            Toast.makeText(this,
                    "Enter the second number",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double secondNumber = Double.parseDouble(currentText);
            double result;

            switch (pendingOperation) {
                case "+":
                    result = calculatorLogic.add(firstNumber, secondNumber);
                    break;

                case "-":
                    result = calculatorLogic.subtract(firstNumber, secondNumber);
                    break;

                case "*":
                    result = calculatorLogic.multiply(firstNumber, secondNumber);
                    break;

                case "/":
                    result = calculatorLogic.divide(firstNumber, secondNumber);
                    break;

                default:
                    return;
            }

            resultText.setText(formatResult(result));

            // Prepare for another calculation
            editText.setText(formatResult(result));
            pendingOperation = "";

        } catch (NumberFormatException e) {
            Toast.makeText(this,
                    "Invalid number",
                    Toast.LENGTH_SHORT).show();

        } catch (IllegalArgumentException e) {
            Toast.makeText(this,
                    "Cannot divide by zero",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private String formatResult(double value) {
        if (Double.isFinite(value)
                && value == Math.rint(value)
                && Math.abs(value) < 1.0e15) {
            return String.valueOf((long) value);
        }

        return String.valueOf(value);
    }
}