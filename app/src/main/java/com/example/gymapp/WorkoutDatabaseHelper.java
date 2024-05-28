package com.example.gymapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

// Database helper class for managing workout data
public class WorkoutDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "workout.db";
    private static final int DATABASE_VERSION = 3;

    // Table and column names for the 'workouts' table
    private static final String TABLE_WORKOUTS = "workouts";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_TARGET = "target";
    private static final String COLUMN_IMAGE_URL = "imageUrl";

    // Table and column names for the 'exercises' table
    private static final String TABLE_EXERCISES = "exercises";
    private static final String COLUMN_EXERCISE_ID = "_id";
    private static final String COLUMN_EXERCISE_NAME = "exercise_name";
    protected static final String COLUMN_EXERCISE_TARGET = "exercise_target";
    private static final String COLUMN_WORKOUT_ID = "workout_id"; // FK in schema

    private Context context;

    // Constructor
    public WorkoutDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create workouts table
        String createWorkoutsTableQuery = "CREATE TABLE " + TABLE_WORKOUTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_TARGET + " TEXT, " +
                COLUMN_IMAGE_URL + " TEXT)";
        db.execSQL(createWorkoutsTableQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade the database
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUTS);
        onCreate(db);
    }

    public long addWorkout(Workout workout) {
        // Add a new workout to the 'workouts' table
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, workout.getName());
        values.put(COLUMN_DESCRIPTION, workout.getDescription());
        values.put(COLUMN_TARGET, workout.getTarget());
        values.put(COLUMN_IMAGE_URL, workout.getImageUrl());
        long workoutId = db.insert(TABLE_WORKOUTS, null, values);

        db.close();
        return workoutId;
    }

    public List<Workout> getAllWorkouts() {
        // Get all workouts from the 'workouts' table
        List<Workout> workouts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_WORKOUTS, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                long workoutId = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));

                // Retrieve exercises for the current workout
                List<Exercise> exercises = getExercisesForWorkout(db, workoutId);

                Workout workout = new Workout(
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_TARGET)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URL)),
                        exercises
                );
                workout.setId(workoutId);
                workouts.add(workout);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return workouts;
    }

    private List<Exercise> getExercisesForWorkout(SQLiteDatabase db, long workoutId) {
        // Get exercises associated with a specific workout
        List<Exercise> exercises = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EXERCISES +
                " WHERE " + COLUMN_WORKOUT_ID + " = " + workoutId, null);

        if (cursor != null && cursor.moveToFirst()) {
            ExerciseDatabaseHelper exerciseDatabaseHelper = new ExerciseDatabaseHelper(context);

            do {
                String exerciseName = cursor.getString(cursor.getColumnIndex(COLUMN_EXERCISE_NAME));
                Exercise exercise = exerciseDatabaseHelper.getExerciseByName(exerciseName);

                if (exercise != null) {
                    exercises.add(exercise);
                }
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        return exercises;
    }


    public void clearAllWorkouts() {
        // Clear all data from the tables
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WORKOUTS, null, null);
        db.delete(TABLE_EXERCISES, null, null);
        db.close();
    }
}

