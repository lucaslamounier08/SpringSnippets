package com.lucaslamounier.usecase.book.adapters.in.web.annotations;

import com.lucaslamounier.usecase.book.adapters.in.web.validator.PublishedDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = PublishedDateValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPublishedDate {
    String message() default "Published date year must be between 2020 and 2120";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
