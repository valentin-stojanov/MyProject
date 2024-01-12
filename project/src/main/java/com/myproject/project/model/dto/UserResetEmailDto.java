package com.myproject.project.model.dto;

import com.myproject.project.model.validation.ExistingUserEmail;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class UserResetEmailDto {

    @Email(message = "User email should be valid.")
    @NotEmpty(message = "User email should be provided.")
    @ExistingUserEmail(message = "Provided email doesn't exist")
    private String email;

    public UserResetEmailDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
