package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

// Sign Up Page
public class SignUpActivity extends AppCompatActivity {

    // Initialize XML elements and db helper
    EditText editTextEmail, editTextPassword, editTextName, editTextPhone;
    Button buttonSignUp;
    DatabaseHelper databaseHelper;

    // Method to navigate back to LoginActivity
    public void backToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        // Initialize Layout Views
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSignUp = findViewById(R.id.buttonRegister);
        databaseHelper = new DatabaseHelper(this);
        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);

        // Sign up button listener
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve user input for email, password, name, and phone
                String email = editTextEmail.getText().toString().trim().toLowerCase();
                String password = editTextPassword.getText().toString().trim();
                String name = editTextName.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();

                // Check if email and password are empty
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                } else {
                    // Insert user data into the database
                    boolean isInserted = databaseHelper.insertData(email, password, name, phone);
                    if (isInserted) {
                        // Notify user of successful sign-up and navigate to LoginActivity
                        Toast.makeText(SignUpActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Notify user of sign-up failure
                        Toast.makeText(SignUpActivity.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
}
