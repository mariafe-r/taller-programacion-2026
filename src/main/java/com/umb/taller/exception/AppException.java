package com.umb.taller.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AppException extends RuntimeException {
    private static final Logger LOGGER = Logger.getLogger(AppException.class.getName());

    private final String errorCode;

    protected AppException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
        LOGGER.log(Level.SEVERE, "[{0}] {1}", new Object[]{errorCode, message});
    }

    protected AppException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        LOGGER.log(Level.SEVERE, "[{0}] {1}", new Object[]{errorCode, message});
        LOGGER.log(Level.SEVERE, "Cause: ", cause);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
