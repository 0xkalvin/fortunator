package com.fortunator.api.controller.exceptions;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fortunator.api.service.exceptions.EmailExistsException;
import com.fortunator.api.service.exceptions.ResourceNotFoundException;
import com.fortunator.api.service.exceptions.UserNotFoundException;


@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EmailExistsException.class)
	public ResponseEntity<StandardError> resourceNotFound(EmailExistsException e, HttpServletRequest request) {
		String error = "Email alredy registered";
		HttpStatus status = HttpStatus.CONFLICT;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(UserNotFoundException e, HttpServletRequest request) {
		HttpStatus status;
		if("User not found".equals(e.getMessage())){
			status = HttpStatus.NOT_FOUND;			
		} else {
			status = HttpStatus.FORBIDDEN;
		}
		
		String error = "User not found";
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		String error = "Resource not found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		 
		List<Field> fields = new ArrayList<>();
		
		for(ObjectError error : ex.getBindingResult().getAllErrors()) {
			String name = ((FieldError) error).getField() ;
			String message = error.getDefaultMessage();
			
			fields.add(new Field(name, message));
		}
		
		StandardError error = new StandardError();
		error.setStatus(status.value());
		error.setMessage("One or more fields were filled in incorrectly");
		error.setTimestamp(Instant.now());
		error.setFields(fields);
		
		return super.handleExceptionInternal(ex, error, headers, status, request);
	}
}