package com.myproject.project.model.dto;

public class UserViewModel {
    private String fullName;
    private String email;
    private int age;

    public UserViewModel() {
    }

    public String getFullName() {
        return fullName;
    }

    public UserViewModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
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
}
