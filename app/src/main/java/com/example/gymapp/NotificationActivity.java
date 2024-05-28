package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);

        String notificationText = getIntent().getStringExtra("notificationText");

        // Display notification text
        TextView notificationTextView = findViewById(R.id.notificationText);
        notificationTextView.setText(notificationText);

        // Dismiss button
        Button dismissButton = findViewById(R.id.dismissButton);
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the notification text
                notificationTextView.setVisibility(View.GONE);
            }
        });

        // Back button functionality
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to HomeActivity
                Intent intent = new Intent(NotificationActivity.this, HomeActivity.class);
                startActivity(intent);
                finish(); // Finish current activity to prevent going back to it when pressing back
            }
        });
    }
}
