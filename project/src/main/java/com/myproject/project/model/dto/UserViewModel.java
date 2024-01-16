package com.myproject.project.model.dto;

public class UserViewModel {
    private String firstName;
    private String lastName;
    private String email;
    private int age;

    public UserViewModel() {
    }

    public String getFullName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public UserViewModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public int getAge() {
        return age;
    }

    public UserViewModel setAge(int age) {
        this.age = age;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserViewModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserViewModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }
}
