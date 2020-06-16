package com.dev.cinema.annotation;

import com.dev.cinema.model.dto.UserRequestDto;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsCheckValidator implements
        ConstraintValidator<PasswordsCheck, UserRequestDto> {

    @Override
    public boolean isValid(UserRequestDto userRegistrationDto, ConstraintValidatorContext
            constraintValidatorContext) {
        return userRegistrationDto.getPassword().equals(userRegistrationDto.getRepeatPassword());
    }
}
