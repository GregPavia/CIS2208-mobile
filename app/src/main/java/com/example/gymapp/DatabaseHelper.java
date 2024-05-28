package com.example.gymapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    //initialize database helper with context
    public DatabaseHelper(Context context) {
        super(context, "SignLog.db", null, 1);
        this.context = context; // Store the context
    }

    public static final String databaseName = "SignLog.db";

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        //create table on create
        MyDatabase.execSQL("create Table users(email TEXT primary key, password TEXT, name TEXT, phone TEXT, goals TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
    }

    public Boolean insertData(String email, String password, String name, String phone){
        //insert data in db from parameters
        email = email.toLowerCase();
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        long result = MyDatabase.insert("users", null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public String getCurrentUserId() {
        //get current user from shared preferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("loggedInUserEmail", ""); // Return the logged-in user's email
    }

    public boolean updateUserGoals(String goals) {
        //update goals from goal in parameter
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("goals", goals);

        // Update the goals column for the current user
        String currentUserEmail = getCurrentUserId();
        int rowsAffected = db.update("users", values, "email = ?", new String[]{currentUserEmail});
        db.close();

        return rowsAffected > 0;
    }

    public boolean clearUserGoals() {
        //clear goals
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("goals", "");

        // Update user goals based on logged-in user's email
        int rowsAffected = MyDatabase.update("users", contentValues, "email = ?", new String[]{getCurrentUserId()});
        return rowsAffected > 0;
    }


    public User getUserDataById(String userId) {
        //get user by id
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{userId});

        if (cursor.moveToFirst()) {
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
            String goals = cursor.getString(cursor.getColumnIndexOrThrow("goals"));

            User user = new User(name, email, phone, goals);

            // Add debug statement to log the retrieved user data
            Log.d("DatabaseHelper", "getUserDataById() - Name: " + name + ", Email: " + email + ", Phone: " + phone);

            return user;
        } else {
            return null; // User not found
        }
    }

    public boolean updateUserData(String name, String email, String phone) {
        //update user info
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("phone", phone);

        int rowsAffected = MyDatabase.update("users", contentValues, "email = ?", new String[]{email});
        return rowsAffected > 0;
    }

    public Boolean checkEmail(String email){
        //check email
        //email turned to lowercase to avoid case issues
        email = email.toLowerCase();
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ?", new String[]{email});

        if(cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }

    public Boolean checkEmailPassword(String email, String password){
        //check password
        //email always lowercase
        email = email.toLowerCase();
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM users WHERE email = ? AND password = ?", new String[]{email, password});

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
