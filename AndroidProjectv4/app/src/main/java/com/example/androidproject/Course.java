package com.example.androidproject;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class Course implements Serializable {
    private String id;
    private String title;
    private String mainTopics;
    private List<String> prerequisites;
    private byte[] photo;

    private  int available;

    public Course() {
    }

    @Override
    public String toString() {
        return title;
    }

    public Course(String title, String mainTopics, List<String> prerequisites, byte[] photo) {
        this.id = generateCourseNumber();
        this.title = title;
        this.mainTopics = mainTopics;
        this.prerequisites = prerequisites;
        this.photo = photo;
        this.available = 0;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMainTopics() {
        return mainTopics;
    }

    public void setMainTopics(String mainTopics) {
        this.mainTopics = mainTopics;
    }

    public List<String> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(List<String> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
