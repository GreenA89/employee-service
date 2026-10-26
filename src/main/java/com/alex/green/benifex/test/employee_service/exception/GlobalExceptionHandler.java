package com.alex.green.benifex.test.employee_service.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DuplicateEmailAddressException.class)
	public ResponseEntity<Error> handleDuplicateEmailException(Exception exception, HttpServletRequest request) {
		return buildError(
			HttpStatus.CONFLICT,
			exception.getMessage(),
			request.getRequestURI()
		);
	}

	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<Error> handleEmployeeNotFoundException(Exception exception, HttpServletRequest request) {
		return buildError(
				HttpStatus.NOT_FOUND,
				exception.getMessage(),
				request.getRequestURI()
		);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Error> handleValidationException(
			MethodArgumentNotValidException exception,
			HttpServletRequest request) {

		String message = exception.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage())
				.collect(Collectors.joining(", "));

		return buildError(
				HttpStatus.BAD_REQUEST,
				message,
				request.getRequestURI()
		);
	}

	private ResponseEntity<Error> buildError(
			HttpStatus status,
			String message,
			String path) {

		Error error = new Error(
				Instant.now(),
				status.value(),
				status.getReasonPhrase(),
				message,
				path
		);

		return ResponseEntity.status(status).body(error);
	}
}
