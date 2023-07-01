package com.example.androidproject;

public class PreviousOffering {
    private String courseNumber;
    private String courseTitle;
    private String date;
    private String time;
    private int numberOfStudents;
    private String venue;
    private String instructorName;

    public PreviousOffering(){

    }

    public PreviousOffering(String courseTitle, String date, String time,
                            int numberOfStudents, String venue, String instructorName) {
        this.courseTitle = courseTitle;
        this.date = date;
        this.time = time;
        this.numberOfStudents = numberOfStudents;
        this.venue = venue;
        this.instructorName = instructorName;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }
}

