package com.byond.shifts.service.shared.http.annotation.date;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateValueValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateValue {
    boolean isRequired();

    String message() default "Invalid date value";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
