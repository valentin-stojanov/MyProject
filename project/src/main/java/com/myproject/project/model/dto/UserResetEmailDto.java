package com.myproject.project.model.dto;

import com.myproject.project.model.validation.ExistingUserEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

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
