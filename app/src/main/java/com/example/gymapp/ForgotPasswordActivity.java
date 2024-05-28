package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

//This is meant to handle forgot password, but as mentioned in doc and below not all functionality is present here
public class ForgotPasswordActivity extends AppCompatActivity {

    EditText editTextEmail;
    Button buttonResetPassword;
    Button backButton;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);

        editTextEmail = findViewById(R.id.editTextEmail);
        buttonResetPassword = findViewById(R.id.buttonResetPassword);
        ImageView buttonBack = findViewById(R.id.buttonBack);
        databaseHelper = new DatabaseHelper(this);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString().trim();

                if (email.isEmpty()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                } else {
                    boolean emailExists = databaseHelper.checkEmail(email);
                    if (emailExists) {
                        // For the case of this app, we aren't handling Resetting passwords, but if I were to do so it would ideally be using firebase, since the project guidelines said to use SQLite, I am not delving further.
                        Toast.makeText(ForgotPasswordActivity.this, "Password reset email sent. Please note that this App Demo doesn't have password reset functionality", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "Email not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
