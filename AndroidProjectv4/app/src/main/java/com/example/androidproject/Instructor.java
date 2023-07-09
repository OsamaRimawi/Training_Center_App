package com.example.androidproject;

import java.util.List;
import java.util.UUID;

public class Instructor {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private byte[] photo;
    private String mobileNumber;
    private String address;
    private String specialization;
    private String degree;
    private List<String> coursesCanTeach;

    private Integer userType;

    public Instructor() {
        this.userType = 2;
    }

    public Instructor(String email, String firstName, String lastName, String password, byte[] photo,
                      String mobileNumber, String address, String specialization, String degree,
                      List<String> coursesCanTeach) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.photo = photo;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.specialization = specialization;
        this.degree = degree;
        this.coursesCanTeach = coursesCanTeach;
        this.userType = 2;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
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

    private String generateInstructorId() {
        return UUID.randomUUID().toString();
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public List<String> getCoursesCanTeach() {
        return coursesCanTeach;
    }

    public void setCoursesCanTeach(List<String> coursesCanTeach) {
        this.coursesCanTeach = coursesCanTeach;
    }
    public String getFullName(){
        return this.firstName + " " + this.lastName;
    }

}

