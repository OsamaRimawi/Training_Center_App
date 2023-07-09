package com.example.androidproject;

public class Admin {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private byte[] photo;
    private Integer userType;

    public Admin() {
        this.userType = 1;
    }

    public Admin(String email, String firstName, String lastName, String password, byte[] photo) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.photo = photo;
        this.userType = 1;
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

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getFullName(){
        return this.firstName + " " + this.lastName;
    }
}
