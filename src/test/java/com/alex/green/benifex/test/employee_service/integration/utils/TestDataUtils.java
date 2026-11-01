package com.alex.green.benifex.test.employee_service.integration.utils;

import com.alex.green.benifex.test.employee_service.dto.request.EmployeeRequest;
import com.alex.green.benifex.test.employee_service.dto.response.EmployeeResponse;
import com.alex.green.benifex.test.employee_service.dto.transfer.EmployeeTransferDTO;
import com.alex.green.benifex.test.employee_service.model.Employee;

import java.time.LocalDate;

public class TestDataUtils {

	public static Employee generateEmployee() {
		LocalDate dob = LocalDate.of(1989, 10, 16);

		Employee employee = new Employee();
		employee.setAddress("123 Apple Street");
		employee.setFirstName("Alex");
		employee.setSurname("Green");
		employee.setEmail("alex.green@benifex.com");
		employee.setDob(dob);
		employee.setGender("Male");
		employee.setTitle("Sir");
		return employee;
	}

	public static EmployeeResponse generateEmployeeResponse() {
		LocalDate dob = LocalDate.of(1989, 10, 16);

		EmployeeResponse employee = new EmployeeResponse();
		employee.setEmployeeNo("BENIFEX-EMP-123");
		employee.setAddress("123 Apple Street");
		employee.setFirstName("Alex");
		employee.setSurname("Green");
		employee.setEmail("alex.green@benifex.com");
		employee.setDob(dob);
		employee.setGender("Male");
		employee.setTitle("Sir");
		return employee;
	}

	public static EmployeeRequest generateEmployeeRequest() {
		LocalDate dob = LocalDate.of(1989, 10, 16);

		EmployeeRequest employee = new EmployeeRequest();
		employee.setAddress("123 Apple Street");
		employee.setFirstName("Alex");
		employee.setSurname("Green");
		employee.setEmail("alex.green@benifex.com");
		employee.setDob(dob);
		employee.setGender("Male");
		employee.setTitle("Sir");
		return employee;
	}

	public static EmployeeTransferDTO generateEmployeeTransferDto() {
		LocalDate dob = LocalDate.of(1989, 10, 16);

		EmployeeTransferDTO employee = new EmployeeTransferDTO();
		employee.setEmployeeNo("BENIFEX-EMP-123");
		employee.setAddress("123 Apple Street");
		employee.setFirstName("Alex");
		employee.setSurname("Green");
		employee.setEmail("alex.green@benifex.com");
		employee.setDob(dob);
		employee.setGender("Male");
		employee.setTitle("Sir");
		return employee;
	}
}
