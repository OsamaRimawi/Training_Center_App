package com.example.androidproject;

public class Registration {
    private int id;
    private String courseOfferingId;
    private String traineeId;
    private int status;

    public Registration() {
    }

    public Registration(int id, String courseOfferingId, String traineeId, int status) {
        this.id = id;
        this.courseOfferingId = courseOfferingId;
        this.traineeId = traineeId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseOfferingId;
    }

    public void setCourseId(String courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public String getStudentEmail() {
        return traineeId;
    }

    public void setStudentEmail(String traineeId) {
        this.traineeId = traineeId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
