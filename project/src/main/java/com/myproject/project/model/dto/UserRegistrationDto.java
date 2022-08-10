package com.myproject.project.model.dto;

import com.myproject.project.model.validation.FieldsMatch;

import javax.validation.constraints.*;

@FieldsMatch(firstField = "password",
        secondField = "confirmPassword",
        message = "Passwords do not match!")
public class UserRegistrationDto {
    @NotBlank
    @Size(min = 2, max = 20)
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 20)
    private String lastName;
    @NotBlank
    @Email
    private String email;
    @Positive
    @Max(100)
    private Integer age;
    @NotBlank
    @Size(min = 5, max = 20)
    private String password;
    private String confirmPassword;

    public UserRegistrationDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public UserRegistrationDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegistrationDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegistrationDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public UserRegistrationDto setAge(Integer age) {
        this.age = age;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegistrationDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegistrationDto setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    @Override
    public String toString() {
        return "UserRegistrationDto{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", password='" + (password != null ? "[PROTECTED]" : null) + '\'' +
                ", confirmPassword='" + (confirmPassword != null ? "[PROTECTED]" : null) + '\'' +
                '}';
    }
}
