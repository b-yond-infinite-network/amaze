package com.byond.shifts.service.shared.http.annotation.number;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LongValueValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface LongValue {
    boolean isRequired();

    String message() default "Invalid long value";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
