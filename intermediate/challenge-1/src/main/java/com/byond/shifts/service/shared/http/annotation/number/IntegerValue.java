package com.byond.shifts.service.shared.http.annotation.number;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IntegerValueValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IntegerValue {
    boolean isRequired();

    String message() default "Invalid integer value";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
