package com.umb.taller.validation;

public class ValidationExample {
    public static void main(String[] args) {
        Validator<String> notEmpty = value -> value != null && !value.trim().isEmpty();
        Validator<String> hasAt = value -> value != null && value.contains("@");

        Validator<String> emailValidator = notEmpty.and(hasAt);

        System.out.println(emailValidator.validate("usuario@email.com"));
        System.out.println(emailValidator.validate("usuario"));
    }
}
