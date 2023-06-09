package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button buttonSum;
    TextView textResult;
    EditText firstNumber;
    EditText secondNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstNumber = findViewById(R.id.textFirstNumber);
        secondNumber = findViewById(R.id.textSecondNumber);
        textResult = findViewById(R.id.textViewResult);

        buttonSum = findViewById(R.id.buttonSum);
        buttonSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = Integer.parseInt(firstNumber.getText().toString());
                int b = Integer.parseInt(secondNumber.getText().toString());
                int sum = a + b;
                textResult.setText("RESULT: " + sum);
            }
        });
    }
}