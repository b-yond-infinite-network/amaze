package com.byond.shifts.service.shared.http.annotation.number;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LongValueValidator implements ConstraintValidator<LongValue, Long> {
    private boolean isRequired;

    @Override
    public void initialize(LongValue constraintAnnotation) {
        isRequired = constraintAnnotation.isRequired();
    }

    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
        return !isRequired || (aLong != null && aLong != 0);
    }
}
