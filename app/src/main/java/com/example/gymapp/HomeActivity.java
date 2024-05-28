package com.example.gymapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


import androidx.annotation.NonNull;

import com.example.gymapp.receivers.NotificationReceiver;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

//Home screen
public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        // Replace the default fragment with HomeFragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new HomeFragment())
                    .commit();
        }

        // Initialize the toolbar and settings
        Toolbar toolbar = findViewById(R.id.toolbar);

        ImageView settingsBTN = findViewById(R.id.userIcon);

        // Set listener for the settings button to navigate to SettingsActivity
        settingsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Initialize the bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set listener for bottom navigation view item selection
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.menu_home) {

                    // Handle each bottom navigation menu item
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainer, new HomeFragment())
                            .commit();
                    return true;
                } if (itemId == R.id.menu_exercise) {
                    // Replace fragment with BMIFragment when BMI is selected
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainer, new ExerciseFragment())
                            .commit();
                    return true;
                } if (itemId == R.id.menu_bmi) {
                    // Replace fragment with BMIFragment when BMI is selected
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainer, new BMIFragment())
                            .commit();
                    return true;
                } else if (itemId == R.id.menu_profile) {
                    // Handle Profile menu item click
                    Intent profileIntent = new Intent(HomeActivity.this, SettingsActivity.class);
                    startActivity(profileIntent);
                    finish();
                    return true;
                }

                return false;
            }
        });

        // Initialize the notification bell icon and listener for button
        ImageView notificationBellIcon = findViewById(R.id.notificationBellIcon);
        notificationBellIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch NotificationActivity
                Intent notificationIntent = new Intent(HomeActivity.this, NotificationActivity.class);
                // Get today's date and construct notification text
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String currentDate = dateFormat.format(new Date());
                String notificationText = currentDate + "\nRemember to Stay Healthy Today!";
                notificationIntent.putExtra("notificationText", notificationText);
                startActivity(notificationIntent);
            }
        });
    }
}
