package com.alex.green.benifex.test.employee_service.exception;

public class DuplicateEmailAddressException extends RuntimeException {

	public DuplicateEmailAddressException(String email) {
		super("An employee with email " + email + " already exists");
	}
}