package com.myproject.project.model.dto;

public class UserProfileEditViewModel {
    private String firstName;
    private String lastName;
    private String email;
    private int age;

    public UserProfileEditViewModel() {
    }

    public String getFirstName() {
        return firstName;
    }

    public UserProfileEditViewModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserProfileEditViewModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserProfileEditViewModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public int getAge() {
        return age;
    }

    public UserProfileEditViewModel setAge(int age) {
        this.age = age;
        return this;
    }
}
