package com.example.androidproject;

import java.util.UUID;

public class User {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private byte[] photo;

    public User(String email, String firstName, String lastName, String password, byte[] photo) {
        this.id = generateCourseNumber();
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.photo = photo;
    }

    private String generateCourseNumber() {
        return UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

}