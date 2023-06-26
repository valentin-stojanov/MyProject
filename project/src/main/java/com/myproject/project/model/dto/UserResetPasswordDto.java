package com.myproject.project.model.dto;

import com.myproject.project.model.validation.FieldsMatch;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@FieldsMatch(firstField = "password", secondField = "confirmPassword", message = "Passwords do not match!")
public class UserResetPasswordDto {

    @NotBlank(message = "Password must not be empty.")
    @Size(min = 5, max = 20, message = "Password must be between 5 and 30 characters.")
    private String password;
    private String confirmPassword;

    public String getPassword() {
        return password;
    }

    public UserResetPasswordDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserResetPasswordDto setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    @Override
    public String toString() {
        return "UserResetPasswordDto{" +
                "password='" + "[PROTECTED]" + '\'' +
                ", confirmPassword='" + "[PROTECTED]" + '\'' +
                '}';
    }
}
