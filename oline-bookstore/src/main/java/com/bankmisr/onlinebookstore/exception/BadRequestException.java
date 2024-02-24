package com.bankmisr.onlinebookstore.exception;

public class BadRequestException extends BookstoreException{
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
