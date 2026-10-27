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
    private ExpressionEvaluator evaluator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText2);
        resultText = findViewById(R.id.resultText);

        evaluator = new ExpressionEvaluator();

        // Number buttons
        int[] numberButtonIds = {
                R.id.num1, R.id.num2, R.id.num3,
                R.id.num4, R.id.num5, R.id.num6,
                R.id.num7, R.id.num8, R.id.num9,
                R.id.zero
        };

        for (int id : numberButtonIds) {
            Button button = findViewById(id);

            button.setOnClickListener(v ->
                    appendText(button.getText().toString()));
        }

        // Decimal point
        findViewById(R.id.dot).setOnClickListener(v ->
                appendText("."));

        // Basic arithmetic operations
        findViewById(R.id.add).setOnClickListener(v ->
                appendText("+"));

        findViewById(R.id.sub).setOnClickListener(v ->
                appendText("-"));

        findViewById(R.id.mul).setOnClickListener(v ->
                appendText("*"));

        findViewById(R.id.div).setOnClickListener(v ->
                appendText("/"));

        // Power
        findViewById(R.id.power).setOnClickListener(v ->
                appendText("^"));

        // Scientific functions
        setFunctionButton(R.id.sin, "sin(");
        setFunctionButton(R.id.cos, "cos(");
        setFunctionButton(R.id.tan, "tan(");
        setFunctionButton(R.id.sqrt, "sqrt(");
        setFunctionButton(R.id.square, "square(");
        setFunctionButton(R.id.log, "log(");
        setFunctionButton(R.id.ln, "ln(");

        // Mathematical constants
        findViewById(R.id.pi).setOnClickListener(v ->
                appendText("pi"));

        findViewById(R.id.euler).setOnClickListener(v ->
                appendText("e"));

        // Parentheses
        findViewById(R.id.openParen).setOnClickListener(v ->
                appendText("("));

        findViewById(R.id.closeParen).setOnClickListener(v ->
                appendText(")"));

        // Clear
        findViewById(R.id.clear_text).setOnClickListener(v -> {
            editText.setText("");
            resultText.setText("0");
        });

        // Calculate
        findViewById(R.id.submit).setOnClickListener(v ->
                calculateResult());
    }

    // Insert text at the current cursor position
    private void appendText(String text) {
        int start = editText.getSelectionStart();
        int end = editText.getSelectionEnd();

        if (start < 0 || end < 0) {
            start = editText.length();
            end = start;
        }

        editText.getText().replace(
                Math.min(start, end),
                Math.max(start, end),
                text
        );

        editText.setSelection(
                Math.min(start, end) + text.length()
        );
    }

    // Connect scientific buttons
    private void setFunctionButton(int buttonId, String function) {
        findViewById(buttonId).setOnClickListener(v ->
                appendText(function));
    }

    // Evaluate the complete expression
    private void calculateResult() {
        String expression = editText.getText().toString().trim();

        if (expression.isEmpty()) {
            Toast.makeText(this,
                    "Enter an expression first",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double result = evaluator.evaluate(expression);

            String formattedResult = formatResult(result);

            resultText.setText(formattedResult);
            editText.setText(formattedResult);
            editText.setSelection(editText.length());

        } catch (IllegalArgumentException e) {
            Toast.makeText(this,
                    e.getMessage() == null
                            ? "Invalid expression"
                            : e.getMessage(),
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