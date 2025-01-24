package com.lucaslamounier.usecase.book.adapters.in.web.annotations;

import com.lucaslamounier.usecase.book.adapters.in.web.validator.PriceValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = PriceValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPrice {
    String message() default "Price must be greater than 0 and less than 999";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
