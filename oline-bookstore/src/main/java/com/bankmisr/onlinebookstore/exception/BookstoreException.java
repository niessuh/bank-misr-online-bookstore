package com.bankmisr.onlinebookstore.exception;

public class BookstoreException extends Exception {
    private final String message;

    public BookstoreException(String message) {
        this.message = message;
    }
    public BookstoreException(String message, Throwable cause) {
        super(cause);
        this.message = message;
    }


    @Override
    public String getMessage() {
        return this.message;
    }
}
