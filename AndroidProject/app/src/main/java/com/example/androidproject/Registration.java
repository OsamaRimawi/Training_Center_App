package com.example.androidproject;

public class Registration {
    private int id;
    private int courseOfferingId;
    private int traineeId;
    private int status;

    public Registration() {
    }

    public Registration(int id, int courseOfferingId, int traineeId, int status) {
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

    public int getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(int courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public int getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(int traineeId) {
        this.traineeId = traineeId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
