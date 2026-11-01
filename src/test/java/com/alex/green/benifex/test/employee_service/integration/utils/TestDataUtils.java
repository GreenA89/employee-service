package com.alex.green.benifex.test.employee_service.integration.utils;

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

}
