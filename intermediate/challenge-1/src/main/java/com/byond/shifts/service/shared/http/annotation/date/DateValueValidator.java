package com.byond.shifts.service.shared.http.annotation.date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class DateValueValidator implements ConstraintValidator<DateValue, String> {
    private boolean isRequired;

    @Override
    public void initialize(DateValue constraintAnnotation) {
        isRequired = constraintAnnotation.isRequired();
    }

    @Override
    public boolean isValid(String dateValue, ConstraintValidatorContext constraintValidatorContext) {
        if (!isRequired)
            return true;
        Pattern p = Pattern.compile("(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/((19|20)\\d\\d)");
        return p.matcher(dateValue).matches();
    }
}
