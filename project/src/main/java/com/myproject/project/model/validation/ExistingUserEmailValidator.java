package com.myproject.project.model.validation;

import com.myproject.project.repository.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistingUserEmailValidator implements ConstraintValidator<ExistingUserEmail, String> {

    private final UserRepository userRepository;

    public ExistingUserEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return this.userRepository
                .findByEmail(email)
                .isPresent();
    }
}
