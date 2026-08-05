package org.junit.jupiter.api;

public class Assertions {
    public static <T extends Throwable> T assertThrows(Class<T> exceptionType, ThrowingRunnable executable) {
        try {
            executable.run();
        } catch (Throwable ex) {
            if (exceptionType.isInstance(ex)) {
                return exceptionType.cast(ex);
            }
            throw new AssertionError("Unexpected exception type: " + ex.getClass().getName(), ex);
        }
        throw new AssertionError("Expected exception of type " + exceptionType.getName());
    }

    public static void assertTrue(boolean condition) {
        if (!condition) {
            throw new AssertionError("Expected condition to be true");
        }
    }

    public static void assertFalse(boolean condition) {
        if (condition) {
            throw new AssertionError("Expected condition to be false");
        }
    }

    public static void assertNotNull(Object value) {
        if (value == null) {
            throw new AssertionError("Expected value to be not null");
        }
    }

    public static <T> void assertEquals(T expected, T actual) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError("Expected: " + expected + " but was: " + actual);
        }
    }

    @FunctionalInterface
    public interface ThrowingRunnable {
        void run() throws Throwable;
    }
}
