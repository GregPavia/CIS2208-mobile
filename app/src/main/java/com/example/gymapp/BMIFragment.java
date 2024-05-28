package com.example.gymapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BMIFragment extends Fragment {

    //initialize buttons
    private EditText weightEditText, heightEditText;
    private Button calculateButton, resetButton;
    private TextView bmiTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bmi_layout, container, false);

        //on create, we initialize these views from the xml code
        weightEditText = view.findViewById(R.id.weightEditText);
        heightEditText = view.findViewById(R.id.heightEditText);
        calculateButton = view.findViewById(R.id.calculateButton);
        resetButton = view.findViewById(R.id.resetButton);
        bmiTextView = view.findViewById(R.id.bmiTextView);

        //below are listeners for buttons
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });

        return view;
    }

    private void calculateBMI() {
        //Calculates BMI using a formula below
        String weightStr = weightEditText.getText().toString();
        String heightStr = heightEditText.getText().toString();

        //make sure fields have info
        if (!weightStr.isEmpty() && !heightStr.isEmpty()) {
            float weight = Float.parseFloat(weightStr);
            float heightInCm = Float.parseFloat(heightStr);
            float heightInM = heightInCm / 100;

            //formula
            float bmi = weight / (heightInM * heightInM);

            //set to 2dp
            bmiTextView.setText("BMI: " + String.format("%.2f", bmi));
            bmiTextView.setVisibility(View.VISIBLE);
        } else {
            //error message for blank fields
            bmiTextView.setText("Please enter weight and height.");
            bmiTextView.setVisibility(View.VISIBLE);
        }
    }

    private void resetFields() {
        //reset button for fields
        weightEditText.setText("");
        heightEditText.setText("");
        bmiTextView.setVisibility(View.GONE);
    }
}

