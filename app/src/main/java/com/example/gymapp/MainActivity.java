package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gymapp.receivers.NotificationScheduler;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WorkoutDatabaseHelper WkdatabaseHelper;
    private ExerciseDatabaseHelper ExdatabaseHelper;
    private DatabaseHelper databaseHelper; // Declare DatabaseHelper instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        NotificationScheduler.scheduleNotification(this);

        // Initialize Firebase (if needed)
        //FirebaseApp.initializeApp(this);

        // Initialize Database Helpers
        databaseHelper = new DatabaseHelper(this);

        WkdatabaseHelper = new WorkoutDatabaseHelper(this);

        ExdatabaseHelper = new ExerciseDatabaseHelper(this);

        if (WkdatabaseHelper.getAllWorkouts().isEmpty()) {
            addDefaultWorkouts();
        }

        if (ExdatabaseHelper.getAllExercises().isEmpty()) {
            // Add default exercises to the database
            addDefaultExercises();
        }

        // Handle display of splash screen for 3000 ms / 3 seconds.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Launch LoginActivity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    // Initialize Workouts
    private void addDefaultWorkouts() {
        // Create Exercise objects for Ab Workout
        List<Exercise> abExercises = new ArrayList<>();
        abExercises.add(new Exercise("Push-ups", "", ""));
        abExercises.add(new Exercise("Plank", "", ""));
        abExercises.add(new Exercise("Russian Twists", "", ""));

        // Create Exercise objects for Arms Workout
        List<Exercise> armsExercises = new ArrayList<>();
        armsExercises.add(new Exercise("Preacher Curl", "", ""));
        armsExercises.add(new Exercise("Bicep Curl", "", ""));
        armsExercises.add(new Exercise("Triceps Dips", "", ""));

        // Create Exercise objects for Leg Day Workout
        List<Exercise> legDayExercises = new ArrayList<>();
        legDayExercises.add(new Exercise("Squats", "", ""));
        legDayExercises.add(new Exercise("Calf Raises", "", ""));
        legDayExercises.add(new Exercise("Leg Lifts", "", ""));

        // Add workouts with associated Exercise lists to WorkoutDatabaseHelper
        WorkoutDatabaseHelper workoutDatabaseHelper = new WorkoutDatabaseHelper(this);

        workoutDatabaseHelper.addWorkout(new Workout("Ab Workout", "Get started working on your dream body with this beginner's workout for Abs\nStart working out now and get a six-pack in no time!", "Beginner", "https://i.imgur.com/UcjDIPi.png", abExercises));

        workoutDatabaseHelper.addWorkout(new Workout("Arms Workout", "Get started working on your dream body with this beginner's workout for Arms\nStart working out now and get some biceps in no time!", "Beginner", "https://i.imgur.com/BbY4Gh5.png", armsExercises));

        workoutDatabaseHelper.addWorkout(new Workout("Leg Day Workout", "Get started working on your dream body with this beginner's workout for Legs\nStart working out now and get footballer's calves in no time!", "Intermediate", "https://i.imgur.com/vnpNqaa.png", legDayExercises));
    }

    //Initialize Exercises
    private void addDefaultExercises() {
        ExdatabaseHelper.addExercise(new Exercise("Push-ups", "1. Start in a plank position with hands shoulder-width apart.\n2. Lower your body until your chest nearly touches the floor.\n3. Push back up to starting position.", "Works chest, shoulders, triceps"));

        ExdatabaseHelper.addExercise(new Exercise("Squats", "1. Stand with feet shoulder-width apart.\n2. Lower your body as if sitting down, keeping your chest up and knees over toes.\n3. Push through heels to stand back up.", "Strengthens legs and core"));

        ExdatabaseHelper.addExercise(new Exercise("Preacher Curl", "1. Sit on a preacher curl bench.\n2. Grip a barbell with underhand grip.\n3. Curl the barbell upwards, contracting the biceps.","Targets biceps"));

        ExdatabaseHelper.addExercise(new Exercise("Bicep Curl", "1. Stand with feet shoulder-width apart, holding dumbbells by your sides.\n2. Curl the dumbbells towards your shoulders, keeping elbows close to your body.\n3. Lower back down with control.","Targets biceps"));

        ExdatabaseHelper.addExercise(new Exercise("Triceps Dips", "1. Sit on a bench with hands gripping the edge.\n2. Slide your hips off the bench and lower your body down by bending your elbows.\n3. Push back up to starting position using your triceps.","Works triceps"));

        ExdatabaseHelper.addExercise(new Exercise("Plank", "1. Start in a push-up position but with elbows bent, resting on forearms.\n2. Keep your body in a straight line from head to heels.\n3. Hold this position for a set time.","Core stability exercise"));

        ExdatabaseHelper.addExercise(new Exercise("Russian Twists",  "1. Sit on the floor with knees bent, feet lifted off the ground, and lean back slightly.\n2. Rotate your torso from side to side, touching the floor beside you with your hands.","Engages core and obliques"));

        ExdatabaseHelper.addExercise(new Exercise("Calf Raises",  "1. Stand on a step or platform with heels hanging off the edge.\n2. Rise up onto your toes, lifting your heels as high as possible.\n3. Lower back down and repeat.", "Targets calf muscles"));

        ExdatabaseHelper.addExercise(new Exercise("Leg Lifts", "1. Lie on your back with legs straight.\n2. Lift legs upward until they are perpendicular to the floor.\n3. Lower legs back down without touching the ground.", "Strengthens lower abs"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close DB to save resources
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}
