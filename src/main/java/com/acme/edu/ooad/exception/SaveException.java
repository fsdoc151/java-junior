package com.acme.edu.ooad.exception;

public class SaveException extends Exception {
    public static final String FILE_NOT_FOUND = "File not found.";
    public static final String UNSUPPORTED_ENCODING = "File encoding unsupported.";
    public static final String ON_CLOSE_FILE_ERROR = "Error occurred when closing file.";
    public static final String ON_WRITE_FILE_ERROR = "Error occurred when writing to file.";

    public SaveException() {
    }

    public SaveException(String message) {
        super(message);
    }

    public SaveException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaveException(Throwable cause) {
        super(cause);
    }
}
