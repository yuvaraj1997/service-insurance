package com.boltech.service_insurance.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<EnumValue, String> {

    private Class<? extends Enum<?>> enumClass;
    private boolean ignoreCase;

    @Override
    public void initialize(EnumValue annotation) {
        this.enumClass = annotation.enumClass();
        this.ignoreCase = annotation.ignoreCase();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Let @NotNull handle null checks
        }

        Enum<?>[] enumConstants = enumClass.getEnumConstants();
        if (ignoreCase) {
            return Arrays.stream(enumConstants)
                    .anyMatch(enumConstant -> enumConstant.name().equalsIgnoreCase(value));
        } else {
            return Arrays.stream(enumConstants)
                    .anyMatch(enumConstant -> enumConstant.name().equals(value));
        }
    }

}
