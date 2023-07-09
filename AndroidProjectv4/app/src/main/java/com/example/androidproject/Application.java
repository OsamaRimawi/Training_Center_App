package com.example.androidproject;

import java.io.Serializable;

public class Application implements Serializable {

    private int id;
    private String studentId;
    private String courseId;
    private int approved;

    public Application() {
    }

    public Application(int id, String studentId, String courseId, int approved) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.approved = approved;
    }

    // Getter and setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }
}

