package com.myproject.project.model.dto;

import com.myproject.project.model.validation.FieldsMatch;
import com.myproject.project.model.validation.UniqueUserEmail;

import javax.validation.constraints.*;

@FieldsMatch(firstField = "password", secondField = "confirmPassword", message = "Passwords do not match!")
public class UserRegistrationDto {
    @NotBlank(message = "First name must not be blank.")
    @Size(min = 2, max = 35,
            message = "First name length must be between 2 and 35 characters.")
    private String firstName;
    @NotBlank(message = "Last name must not be blank.")
    @Size(min = 2, max = 35,
            message = "Last name length must be between 2 and 35 characters.")
    private String lastName;
    @NotBlank(message = "User email should be provided.")
    @Email(message = "User email should be valid.")
    @UniqueUserEmail(message = "The email is already occupied")
    private String email;

    @NotNull(message = "Age must not be empty.")
    @Positive(message = "Age must be positive.")
    @Max(value = 100, message = "Max age is 100.")
    private Integer age;
    @NotBlank(message = "Password must not be empty.")
    @Size(min = 5, max = 20, message = "Password must be between 5 and 30 characters.")
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
        return "UserRegistrationDto{" + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", email='" + email + '\'' + ", age=" + age + ", password='" + (password != null ? "[PROTECTED]" : null) + '\'' + ", confirmPassword='" + (confirmPassword != null ? "[PROTECTED]" : null) + '\'' + '}';
    }
}
