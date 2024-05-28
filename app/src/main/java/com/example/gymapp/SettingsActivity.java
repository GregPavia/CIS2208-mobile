package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private EditText fullNameEditText, phoneEditText, emailEditText, goalsEditText;
    private Button saveButton, saveGoalsButton, clearGoalsButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Initialize Views
        fullNameEditText = findViewById(R.id.fullNameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        emailEditText = findViewById(R.id.emailEditText);
        goalsEditText = findViewById(R.id.goalsEditText);

        // Load user data from SQLite database
        loadUserData();

        saveButton = findViewById(R.id.saveButton);
        saveGoalsButton = findViewById(R.id.saveGoalsButton);
        clearGoalsButton = findViewById(R.id.clearGoalsButton);

        // Save Button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
            }
        });

        // Save Goals Button
        saveGoalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserGoals();
            }
        });

        // Clear Goals Button
        clearGoalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearUserGoals();
            }
        });

        // Back Button
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Logout Button
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear user data
                clearUserDataFields();

                // Log out the user
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Save user's goals
    private void saveUserGoals() {
        String goals = goalsEditText.getText().toString().trim();

        // Update user goals in database
        boolean isUpdated = databaseHelper.updateUserGoals(goals);
        if (isUpdated) {
            Toast.makeText(this, "Goals saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to save goals", Toast.LENGTH_SHORT).show();
        }
    }

    // Clear user goals
    private void clearUserGoals() {
        goalsEditText.setText(""); // Clear goals
    }

    // Handle loading user's data
    private void loadUserData() {
        String userId = databaseHelper.getCurrentUserId(); // Retrieve current user's ID

        // Retrieve user data based on the current user's ID
        User user = databaseHelper.getUserDataById(userId);

        if (user != null) {
            // Set fields with user data
            fullNameEditText.setText(user.getName());
            phoneEditText.setText(user.getPhone());
            emailEditText.setText(user.getEmail());
            goalsEditText.setText(user.getGoals());
        }
    }

    // Save button functionality
    private void saveUserData() {
        // Get user inputs
        String fullName = fullNameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();

        // Validate user inputs
        if (fullName.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update user data in SQLite database
        boolean isUpdated = databaseHelper.updateUserData(fullName, email, phone);
        if (isUpdated) {
            Toast.makeText(this, "Information saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to save user data", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle clearing of fields
    private void clearUserDataFields() {
        fullNameEditText.setText("");
        phoneEditText.setText("");
        emailEditText.setText("");
    }
}
