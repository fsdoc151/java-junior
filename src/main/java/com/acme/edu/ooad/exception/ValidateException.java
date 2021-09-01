package com.acme.edu.ooad.exception;

public class ValidateException extends Exception {
    public static final String NULL_INPUT_ERROR = "Message to save is null.";
    public static final String EMPTY_INPUT_ERROR = "Message to save is empty.";

    public ValidateException() {
    }

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidateException(Throwable cause) {
        super(cause);
    }
}
