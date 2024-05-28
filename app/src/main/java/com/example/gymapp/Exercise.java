package com.example.gymapp;

import android.os.Parcel;
import android.os.Parcelable;

//Represents a single exercise in the gym.
public class Exercise implements Parcelable {
    private long id; // Unique identifier for the exercise
    private String name;
    private String description;
    private String target;

    //Public constructor for exercise. Initialize the name, description, and target of the exercise.
    public Exercise(String name, String description, String target) {
        this.name = name;
        this.description = description;
        this.target = target;
    }

    // Parcelable implementation methods. Takes a Parcel and reads the object data from it.
    protected Exercise(Parcel in) {
        id = in.readLong();
        name = in.readString();
        description = in.readString();
        target = in.readString();
    }

    // Creator constant for Parcelable implementation.
    // It provides a way to create instances of the Parcelable class from a Parcel.
    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(target);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getters and setters for exercise attributes
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
