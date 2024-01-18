package com.nnk.springboot.services.exceptions;

public class Assert extends org.springframework.util.Assert{
    public static void isFound(boolean expression, String message) {
        if (!expression) {
            throw new NotFoundException(message);
        }
    }

    public static void isNotFound(boolean expression, String message) {
        if (expression) {
            throw new IllegalArgumentException(message);
        }
    }
}