package org.slf4j;

public class LoggerFactory {
    public static Logger getLogger(Class<?> clazz) {
        return new Logger() {
            @Override
            public void error(String message) {
                System.err.println("[ERROR] " + message);
            }

            @Override
            public void error(String message, Object... arguments) {
                System.err.println("[ERROR] " + message.replace("{}", String.valueOf(arguments.length > 0 ? arguments[0] : "")));
            }

            @Override
            public void error(String message, Throwable throwable) {
                System.err.println("[ERROR] " + message);
                throwable.printStackTrace(System.err);
            }
        };
    }
}
