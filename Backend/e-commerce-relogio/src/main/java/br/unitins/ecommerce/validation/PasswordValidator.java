package br.unitins.ecommerce.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<SenhaForte, String> {

    @Override
    public void initialize(SenhaForte annotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.length() < 6) {
            return false;
        }
        // Check for at least one uppercase, one lowercase, one digit
        boolean hasUppercase = value.matches(".*[A-Z].*");
        boolean hasLowercase = value.matches(".*[a-z].*");
        boolean hasDigit = value.matches(".*\\d.*");
        
        return hasUppercase && hasLowercase && hasDigit;
    }
}
