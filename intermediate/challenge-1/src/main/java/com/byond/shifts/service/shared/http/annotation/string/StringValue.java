package com.byond.shifts.service.shared.http.annotation.string;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StringValueValidator.class)
@Target({ElementType.PARAMETER,ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StringValue {
    boolean isRequired();

    String message() default "Invalid string value";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
