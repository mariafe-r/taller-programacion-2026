package org.slf4j;

public interface Logger {
    void error(String message);

    void error(String message, Object... arguments);

    void error(String message, Throwable throwable);
}
