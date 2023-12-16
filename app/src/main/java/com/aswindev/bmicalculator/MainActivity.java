package com.aswindev.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView resultText;
    private RadioButton maleButton, femaleButton;
    private EditText ageText, feetText, inchesText, weightText;
    private Button calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String alertText = "Welcome to the BMI Calculator App!";
        Toast.makeText(this, alertText, Toast.LENGTH_LONG).show();

        findViews();
        setupButtonClickListener();
    }

    private void findViews() {
        resultText = findViewById(R.id.text_view_result);

        maleButton = findViewById(R.id.radio_button_male);
        femaleButton = findViewById(R.id.radio_button_female);

        ageText = findViewById(R.id.edit_text_age);
        feetText = findViewById(R.id.edit_text_feet);
        inchesText = findViewById(R.id.edit_text_inches);
        weightText = findViewById(R.id.edit_text_weight);

        calculateButton = findViewById(R.id.button_calculate);
    }

    private void setupButtonClickListener() {
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Calculating BMI...", Toast.LENGTH_SHORT).show();
                double bmi = calculateBMI();
                int age = Integer.parseInt(ageText.getText().toString());
                if (age >= 18) {
                    displayResult(bmi);
                } else {
                    displayGuidance(bmi);
                }
            }
        });
    }



    private double calculateBMI() {
        int feet = 0, inches = 0;
        double weight = 0;

        try {
            feet = Integer.parseInt(feetText.getText().toString());
            inches = Integer.parseInt(inchesText.getText().toString());
            weight = Double.parseDouble(weightText.getText().toString());
        } catch (NumberFormatException e) {
            System.out.println("Got exception: " + e);
        }

        int heightInch = (feet * 12) + inches;
        double heightMeters = heightInch * 0.0254;
        return weight / (heightMeters * heightMeters);
    }

    private void displayResult(double bmi) {
        DecimalFormat formatter = new DecimalFormat("0.00");
        formatter.setRoundingMode(RoundingMode.DOWN);
        String bmiStr = formatter.format(bmi);
        String commentStr = "";
        if (bmi < 18.5) {
            commentStr = "You are underweight.";
        } else if (bmi > 25) {
            commentStr = "You are overweight.";
        } else {
            commentStr = "You are of healthy weight.";
        }
        resultText.setText("BMI is " + bmiStr + ". " + commentStr);
    }

    private void displayGuidance(double bmi) {
        DecimalFormat formatter = new DecimalFormat("0.00");
        formatter.setRoundingMode(RoundingMode.DOWN);
        String bmiStr = formatter.format(bmi);
        String commentStr = "";
        if(maleButton.isChecked()) {
            commentStr = "As you are under 18, please consult your doctor for the healthy range for boys";
        } else if (femaleButton.isChecked()) {
            commentStr = "As you are under 18, please consult your doctor for the healthy range for girls";
        } else {
            commentStr = "As you are under 18, please consult your doctor for the healthy range";
        }
        resultText.setText("BMI is " + bmiStr + ". " + commentStr);
    }

}