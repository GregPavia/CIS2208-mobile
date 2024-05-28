package com.example.gymapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

//Exercise database helper class, used for operations involving exercise DB
public class ExerciseDatabaseHelper extends SQLiteOpenHelper {
    protected static final String DATABASE_NAME = "exercise.db";
    private static final int DATABASE_VERSION = 2;

    // Table and column names
    protected static final String TABLE_EXERCISES = "exercises";
    protected static final String COLUMN_ID = "_id";
    protected static final String COLUMN_NAME = "name";
    protected static final String COLUMN_DESCRIPTION = "description";
    protected static final String COLUMN_TARGET = "target";

    public ExerciseDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Method called when the database is created for the first time.
        String createTableQuery = "CREATE TABLE " + TABLE_EXERCISES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_TARGET + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Method call when the db needs to be upgraded.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
        onCreate(db);
    }

    public long addExercise(Exercise exercise) {
        // add a new exercise to the database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, exercise.getName());
        values.put(COLUMN_DESCRIPTION, exercise.getDescription());
        values.put(COLUMN_TARGET, exercise.getTarget());
        long id = db.insert(TABLE_EXERCISES, null, values);
        db.close();
        return id;
    }

    public List<Exercise> getAllExercises() {
        // retrieve all exercises from the database.
        List<Exercise> exercises = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EXERCISES, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Exercise exercise = new Exercise(
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_TARGET))
                );
                exercise.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                exercises.add(exercise);
            } while (cursor.moveToNext());
        }

        // Close cursor and database connection
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return exercises;
    }

    public Exercise getExerciseByName(String exerciseName) {
        // retrieve an exercise by its name from the database.
        SQLiteDatabase db = this.getReadableDatabase();
        Exercise exercise = null;

        Cursor cursor = db.query(TABLE_EXERCISES, null, COLUMN_NAME + "=?",
                new String[]{exerciseName}, null, null, null);

        // If cursor is not null and contains data, populate the exercise object
        if (cursor != null && cursor.moveToFirst()) {
            exercise = new Exercise(
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TARGET))
            );
            exercise.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
        }

        // Close cursor and database connection
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return exercise;
    }


    public void clearAllExercises() {
        //clear all exercises from the database.
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXERCISES, null, null);
        db.close();
    }

    public Exercise getExerciseById(long id) {
        // retrieve an exercise by its ID from the database.
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_EXERCISES, null, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);

        Exercise exercise = null;
        // If the cursor is not null and contains data, populate the exercise object
        if (cursor != null && cursor.moveToFirst()) {
            exercise = new Exercise(
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TARGET))
            );
            exercise.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
        }

        // Close cursor and database connection
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return exercise;
    }
}
