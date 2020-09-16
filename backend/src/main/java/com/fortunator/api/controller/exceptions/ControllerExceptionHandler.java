package com.fortunator.api.controller.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fortunator.api.service.exceptions.EmailExistsException;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(EmailExistsException.class)
	public ResponseEntity<StandardError> resourceNotFound(EmailExistsException e, HttpServletRequest request){
		String error = "Email alredy registered";
		HttpStatus status = HttpStatus.CONFLICT;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
}
