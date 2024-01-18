package com.myproject.project.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class UserProfileEditDto {

    @NotBlank(message = "First name must not be blank.")
    @Size(min = 2, max = 35,
            message = "First name length must be between 2 and 35 characters.")
    private String firstName;

    @NotBlank(message = "First name must not be blank.")
    @Size(min = 2, max = 35,
            message = "First name length must be between 2 and 35 characters.")
    private String lastName;

    @Positive(message = "Age must be positive.")
    @Max(value = 100, message = "Max age is 100.")
    private int age;

    public UserProfileEditDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public UserProfileEditDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserProfileEditDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public int getAge() {
        return age;
    }

    public UserProfileEditDto setAge(int age) {
        this.age = age;
        return this;
    }
}
