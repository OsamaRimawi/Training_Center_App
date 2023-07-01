package com.example.androidproject;

public class Notification {
    private int id;
    private String title;
    private String message;
    private String studentEmail;

    public Notification() {
    }

    public Notification(int id, String title, String message, String studentEmail) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.studentEmail = studentEmail;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }
}
