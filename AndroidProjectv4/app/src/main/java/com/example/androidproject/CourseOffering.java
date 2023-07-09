package com.example.androidproject;

public class CourseOffering {
    private int id;
    private String courseId;
    private String instructorEmail;
    private String registrationDeadline;
    private String startDate;
    private String schedule;
    private String venue;

    public CourseOffering() {
    }

    public CourseOffering(String courseId, String instructorEmail, String registrationDeadline, String startDate, String schedule, String venue) {
        this.courseId = courseId;
        this.instructorEmail = instructorEmail;
        this.registrationDeadline = registrationDeadline;
        this.startDate = startDate;
        this.schedule = schedule;
        this.venue = venue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public String getRegistrationDeadline() {
        return registrationDeadline;
    }

    public void setRegistrationDeadline(String registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

}

