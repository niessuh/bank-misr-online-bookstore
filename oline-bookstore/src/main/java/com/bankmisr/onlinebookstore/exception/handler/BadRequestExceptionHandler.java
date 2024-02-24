package com.bankmisr.onlinebookstore.exception.handler;

import com.bankmisr.onlinebookstore.exception.BadRequestException;
import com.bankmisr.onlinebookstore.model.ResponseMsg;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class BadRequestExceptionHandler {

    /**
     * Handle bad request exception.
     *
     * @param ex the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {

        ResponseMsg hlpResponse = new ResponseMsg(ex.getMessage());

        return new ResponseEntity<>(hlpResponse, HttpStatus.BAD_REQUEST);
    }
}
