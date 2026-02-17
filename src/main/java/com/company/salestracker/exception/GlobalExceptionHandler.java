package com.company.salestracker.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.company.salestracker.errorResponse.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	
	public ResponseEntity<ErrorResponse> creatError(Exception ex, HttpStatus status, HttpServletRequest request) {
		ErrorResponse err = new ErrorResponse(LocalDateTime.now(), status.value(), status.getReasonPhrase(),
				ex.getMessage(), request.getRequestURI(), null);
		return new ResponseEntity<>(err, status);

	}
	
	@ExceptionHandler(ReasourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFound(ReasourceNotFoundException ex,
			HttpServletRequest request) {

		return creatError(ex, HttpStatus.NOT_FOUND, request);
	}
	
	
	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFound(EmailAlreadyExistsException ex,
			HttpServletRequest request) {

		return creatError(ex, HttpStatus.CONFLICT, request);
	}
	
	@ExceptionHandler(RoleAlreadyExistsException .class)
	public ResponseEntity<ErrorResponse> handleResourceNotFound(RoleAlreadyExistsException  ex,
			HttpServletRequest request) {
		
		return creatError(ex, HttpStatus.CONFLICT, request);
	}
	
	@ExceptionHandler(UnauthorizedException .class)
	public ResponseEntity<ErrorResponse> handleResourceNotFound(UnauthorizedException  ex,
			HttpServletRequest request) {
		
		return creatError(ex, HttpStatus.FORBIDDEN, request);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		Map<String, String> fieldErrors = new HashMap<>();

		ex.getBindingResult().getFieldErrors().forEach(error -> {
			fieldErrors.put(error.getField(), error.getDefaultMessage());
		});

		ErrorResponse err = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(), "Validation failed", request.getRequestURI(), fieldErrors);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

}
