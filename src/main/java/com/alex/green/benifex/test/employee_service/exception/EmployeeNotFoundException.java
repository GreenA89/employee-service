package com.alex.green.benifex.test.employee_service.exception;

public class EmployeeNotFoundException extends RuntimeException {

	public EmployeeNotFoundException(String employeeNo) {
		super("Employee not found with employee number: " + employeeNo);
	}
}