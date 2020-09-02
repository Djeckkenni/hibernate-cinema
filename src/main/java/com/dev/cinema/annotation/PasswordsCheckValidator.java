package com.dev.cinema.annotation;

import com.dev.cinema.model.dto.UserRequestDto;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsCheckValidator implements
        ConstraintValidator<PasswordsCheck, UserRequestDto> {

    @Override
    public boolean isValid(UserRequestDto userRequestDto, ConstraintValidatorContext
            constraintValidatorContext) {
        String password = userRequestDto.getPassword();
        String repeatPassword = userRequestDto.getRepeatPassword();
        return password != null && password.equals(repeatPassword);
    }
}
