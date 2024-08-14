package com.LP2.Trabalho.annotation;

import com.LP2.Trabalho.validation.ContatoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ContatoValidator.class)
public @interface ContatoValidation {
    Class<?>[] groups() default {};

    String message() default "Contato inválido";

    Class<? extends Payload>[] payload() default {};
}
