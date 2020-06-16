package com.dev.cinema.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailConstraint, String> {
    private static final String VALID_EMAIL_REGEX = ".+@.+\\..+";

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email != null && email.matches(VALID_EMAIL_REGEX)
                && email.length() > 8 && email.length() < 30;
    }
}
