package com.lucaslamounier.usecase.book.adapters.in.web.validator;

import com.lucaslamounier.usecase.book.adapters.in.web.annotations.ValidPublishedDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class PublishedDateValidator implements ConstraintValidator<ValidPublishedDate, LocalDate> {

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        if (date == null) {
            return true;
        }

        int year = date.getYear();

        return year >= 1500 && year <= 2120;
    }
}
