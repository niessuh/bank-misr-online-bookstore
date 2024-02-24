package com.bankmisr.onlinebookstore.exception.handler;

import com.bankmisr.onlinebookstore.exception.NotFoundException;
import com.bankmisr.onlinebookstore.model.ResponseMsg;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class NotFoundExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public final ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {

		ResponseMsg hlpResponse = new ResponseMsg(ex.getMessage());

		return new ResponseEntity<>(hlpResponse, HttpStatus.NOT_FOUND);
	}
}