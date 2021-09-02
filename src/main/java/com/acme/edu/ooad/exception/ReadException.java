package com.acme.edu.ooad.exception;

public class ReadException extends Exception {
    public static final String ON_OPEN_FILE_ERROR = "Error occurred when opening file for reading.";

    public ReadException() {
    }

    public ReadException(String message) {
        super(message);
    }

    public ReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReadException(Throwable cause) {
        super(cause);
    }

}
