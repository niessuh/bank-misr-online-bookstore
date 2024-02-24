package com.bankmisr.onlinebookstore.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BookstoreException {

    public NotFoundException() {
        super( "The request is invalid");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
