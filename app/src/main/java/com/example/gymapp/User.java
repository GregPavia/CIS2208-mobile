package com.example.gymapp;

public class User {
    private String name;
    private String email;
    private String phone;

    private String goals;

    // Empty constructor needed for Firebase
    public User() {
    }

    // Constructor for basic user details
    public User(String name, String email, String phone, String goals) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.goals = goals;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGoals() {return goals;}

    public void setGoals(String goals) {this.goals = goals;}
}
