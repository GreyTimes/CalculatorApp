package com.example.calculatorapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvDisplay;
    private CalculatorLogic calculatorLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link UI components
        tvDisplay = findViewById(R.id.tvDisplay);

        // Initialize logic handler
        calculatorLogic = new CalculatorLogic();

        // TODO: Teammates will wire up buttons here
    }
}