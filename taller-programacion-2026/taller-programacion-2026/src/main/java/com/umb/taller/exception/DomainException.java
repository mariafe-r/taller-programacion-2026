package com.umb.taller.exception;

public class DomainException extends AppException {
    public DomainException(String message) {
        super(message, "DOMAIN_ERROR");
    }

    public DomainException(String message, Throwable cause) {
        super(message, "DOMAIN_ERROR", cause);
    }
}
