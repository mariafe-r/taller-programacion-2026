package com.umb.taller.validation;

@FunctionalInterface
public interface Validator<T> {
    boolean validate(T value);

    default Validator<T> and(Validator<? super T> other) {
        return value -> this.validate(value) && other.validate(value);
    }

    default Validator<T> or(Validator<? super T> other) {
        return value -> this.validate(value) || other.validate(value);
    }
}
