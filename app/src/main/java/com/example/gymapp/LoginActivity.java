package com.example.gymapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//Login page and functionality
public class LoginActivity extends AppCompatActivity {

    private TextView textViewRegister;
    private TextView textViewForgotPassword;
    EditText editTextEmail, editTextPassword;
    Button buttonLogin;

    // Database helper and shared preferences
    DatabaseHelper databaseHelper;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Method called when the activity is created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Initialize view xml elements
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        databaseHelper = new DatabaseHelper(this);
        textViewRegister = findViewById(R.id.textViewRegister);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);

        // Initialize database helper and shared preferences
        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);

        // Set listener for the login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve email and password entered by the user
                // Email saved as lower case
                String email = editTextEmail.getText().toString().trim().toLowerCase();
                String password = editTextPassword.getText().toString().trim();

                // Check if email and password are empty
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if login is successful
                    boolean loginSuccess = databaseHelper.checkEmailPassword(email, password);
                    if (loginSuccess) {
                        // Save the logged-in user's email in shared preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("loggedInUserEmail", email);
                        editor.apply();

                        // Notify user of successful login and navigate to HomeActivity
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Notify user of invalid email or password
                        Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Set listener for the "Register" button
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Set listener for the "Forgot Password" button
        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
