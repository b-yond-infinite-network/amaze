package com.byond.shifts.service.shared.http.annotation.date;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TimeValueValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeValue {
    boolean isRequired();

    String message() default "Invalid time value";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
