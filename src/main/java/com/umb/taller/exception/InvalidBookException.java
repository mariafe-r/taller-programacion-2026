package com.umb.taller.exception;

public class InvalidBookException extends DomainException {
    public InvalidBookException(String message) {
        super(message);
    }
}
