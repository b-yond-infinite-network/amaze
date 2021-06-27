package com.byond.shifts.service.shared.http.annotation.date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class TimeValueValidator implements ConstraintValidator<TimeValue, String> {
    private boolean isRequired;

    @Override
    public void initialize(TimeValue constraintAnnotation) {
        isRequired = constraintAnnotation.isRequired();
    }

    @Override
    public boolean isValid(String timeValue, ConstraintValidatorContext constraintValidatorContext) {
        if (!isRequired)
            return true;
        //24 hours format hh:mm:ss
        Pattern p = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
        return p.matcher(timeValue).matches();
    }
}
