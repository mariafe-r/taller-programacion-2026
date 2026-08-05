package com.umb.taller.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AppException extends RuntimeException {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppException.class);

    private final String errorCode;

    protected AppException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
        LOGGER.error("[{}] {}", errorCode, message);
    }

    protected AppException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        LOGGER.error("[{}] {}", errorCode, message, cause);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
