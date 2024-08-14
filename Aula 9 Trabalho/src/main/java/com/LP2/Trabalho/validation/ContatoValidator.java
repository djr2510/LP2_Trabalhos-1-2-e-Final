package com.LP2.Trabalho.validation;

import com.LP2.Trabalho.annotation.ContatoValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
public class ContatoValidator implements ConstraintValidator<ContatoValidation, Integer> {

    @Override
    public void initialize(final ContatoValidation constraintAnnotation) {
    }

    public boolean isValid(Integer Telefone, final ConstraintValidatorContext context) {
        boolean result = false;
        if ( Telefone == null || Telefone.compareTo(0) == 0) {
            result = false;
        } else {
            result = Telefone.compareTo(00000000) >= 0 && Telefone.compareTo(00000000) <= 0 ? true : false;
        }
        return result;
    }
}
