package com.example.gymapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

// Represents a workout
public class Workout implements Parcelable {

    // Fields representing workout attributes
    private long id;
    private String name;
    private String description;
    private String target;
    private String imageUrl;
    private List<Exercise> exercises;

    // Constructor to initialize a Workout object
    public Workout(String name, String description, String target, String imageUrl, List<Exercise> exercises) {
        this.name = name;
        this.description = description;
        this.target = target;
        this.imageUrl = imageUrl;
        this.exercises = exercises;
    }

    // Parcelable implementation methods
    // Constructor used for parceling
    protected Workout(Parcel in) {
        id = in.readLong();
        name = in.readString();
        description = in.readString();
        target = in.readString();
        imageUrl = in.readString();
        exercises = in.createTypedArrayList(Exercise.CREATOR);
    }

    // Creator object for Parcelable implementation
    public static final Creator<Workout> CREATOR = new Creator<Workout>() {
        @Override
        public Workout createFromParcel(Parcel in) {
            return new Workout(in);
        }

        @Override
        public Workout[] newArray(int size) {
            return new Workout[size];
        }
    };

    // Writes the object's data to a parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(target);
        dest.writeString(imageUrl);
        dest.writeTypedList(exercises);
    }

    // Describes special objects contained in the parcel
    @Override
    public int describeContents() {
        return 0;
    }

    // Getters and setters for workout attributes
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
