package com.lucaslamounier.usecase.book.adapters.in.web.validator;

import com.lucaslamounier.usecase.book.adapters.in.web.annotations.ValidPrice;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class PriceValidator implements ConstraintValidator<ValidPrice, BigDecimal> {

    @Override
    public boolean isValid(BigDecimal price, ConstraintValidatorContext context) {
        if (price == null) {
            return true;
        }

        return price.compareTo(BigDecimal.ZERO) > 0 && price.compareTo(new BigDecimal("999")) < 0;
    }
}
