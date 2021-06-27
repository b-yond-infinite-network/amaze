package com.byond.shifts.service.shared.http.annotation.string;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StringValueValidator implements ConstraintValidator<StringValue, String> {
    private boolean isRequired;

    @Override
    public void initialize(StringValue constraintAnnotation) {
        isRequired = constraintAnnotation.isRequired();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (isRequired) {
            return !StringUtils.isEmpty(value);
        }
        return true;
    }
}
