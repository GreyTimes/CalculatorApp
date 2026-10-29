package com.example.calculatorapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView resultText;
    private ExpressionTokenizer tokenizer;

    private ExpressionEvaluator evaluator = new ExpressionEvaluator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Σύνδεση στοιχείων UI
        editText = findViewById(R.id.editText2);
        resultText = findViewById(R.id.resultText);

        // Αρχικοποίηση του Tokenizer
        tokenizer = new ExpressionTokenizer();

        // --- 1. Σύνδεση Αριθμών ---
        setupButton(R.id.num1, "1");
        setupButton(R.id.num2, "2");
        setupButton(R.id.num3, "3");
        setupButton(R.id.num4, "4");
        setupButton(R.id.num5, "5");
        setupButton(R.id.num6, "6");
        setupButton(R.id.num7, "7");
        setupButton(R.id.num8, "8");
        setupButton(R.id.num9, "9");
        setupButton(R.id.zero, "0");

        // --- 2. Σύνδεση Βασικών Πράξεων & Συμβόλων ---
        setupButton(R.id.add, "+");
        setupButton(R.id.sub, "-");
        setupButton(R.id.mul, "*");
        setupButton(R.id.div, "/");
        setupButton(R.id.dot, ".");
        setupButton(R.id.openParen, "(");
        setupButton(R.id.closeParen, ")");
        setupButton(R.id.power, "^");
        setupButton(R.id.sqrt, "√");

        // --- 3. Σύνδεση Επιστημονικών Συναρτήσεων ---
        setupFunctionButton(R.id.sin, "sin(");
        setupFunctionButton(R.id.cos, "cos(");
        setupFunctionButton(R.id.tan, "tan(");
        setupFunctionButton(R.id.log, "log(");
        setupFunctionButton(R.id.ln, "ln(");
        setupFunctionButton(R.id.square, "^2");

        // --- 4. Σύνδεση Σταθερών ---
        setupButton(R.id.pi, "pi"); // Χρησιμοποιείται "pi" για να το διαβάζει σωστά ο Tokenizer
        setupButton(R.id.euler, "e");

        // --- 5. Κουμπί Clear (CE) ---
        Button clearButton = findViewById(R.id.clear_text);
        clearButton.setOnClickListener(v -> {
            editText.setText("");
            resultText.setText("0");
        });

        // --- 6. Κουμπί Submit (=) ---
        Button submitButton = findViewById(R.id.submit);
        submitButton.setOnClickListener(v -> evaluateExpression());
    }

    // Βοηθητική μέθοδος για απλή προσθήκη χαρακτήρα/συμβόλου
    private void setupButton(int buttonId, String value) {
        Button btn = findViewById(buttonId);
        if (btn != null) {
            btn.setOnClickListener(v -> {
                String current = editText.getText().toString();
                editText.setText(current + value);
            });
        }
    }

    // Βοηθητική μέθοδος για συναρτήσεις που ανοίγουν παρενθέσεις αυτόματα
    private void setupFunctionButton(int buttonId, String functionSyntax) {
        Button btn = findViewById(buttonId);
        if (btn != null) {
            btn.setOnClickListener(v -> {
                String current = editText.getText().toString();
                editText.setText(current + functionSyntax);
            });
        }
    }

    // Η κεντρική λογική επικύρωσης και ανάλυσης της έκφρασης
    private void evaluateExpression() {
        String expression = editText.getText().toString();

        if (expression.isEmpty()) {
            Toast.makeText(this, "Παρακαλώ εισάγετε μια έκφραση", Toast.LENGTH_SHORT).show();
            return;
        }

        // Βήμα 1ο: Έλεγχος εγκυρότητας μέσω του ExpressionValidator
        if (!ExpressionValidator.isValid(expression)) {
            Toast.makeText(this, "Μη έγκυρη σύνταξη έκφρασης!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Βήμα 2ο: Μετατροπή σε Tokens μέσω του ExpressionTokenizer
            List<String> tokens = tokenizer.tokenize(expression);

            double finalResult = evaluator.evaluate(tokens);

            resultText.setText(String.valueOf(finalResult));

        } catch (Exception e) {
            Toast.makeText(this, "Σφάλμα: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}