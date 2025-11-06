package br.unitins.ecommerce.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface SenhaForte {
    String message() default "Senha deve conter pelo menos uma letra maiúscula, uma minúscula e um dígito";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
