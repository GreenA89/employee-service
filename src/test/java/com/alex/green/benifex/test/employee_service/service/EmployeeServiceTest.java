package com.alex.green.benifex.test.employee_service.service;

import com.alex.green.benifex.test.employee_service.exception.DuplicateEmailAddressException;
import com.alex.green.benifex.test.employee_service.exception.EmployeeNotFoundException;
import com.alex.green.benifex.test.employee_service.model.Employee;
import com.alex.green.benifex.test.employee_service.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTests {

	private static final String EMPLOYEE_NUMBER_PREFIX =  "BENIFEX-EMP-";

	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private EmployeeService employeeService;

	@Test
	void shouldThrowExceptionWhenCreatingEmployeeRecordWithExistingEmail() {
		when(employeeRepository.existsByEmail("alex.green@benifex.com")).thenReturn(true);

		Exception e = assertThrows(DuplicateEmailAddressException.class, () -> employeeService.createEmployee(generateEmployee()));
		assertEquals("An employee with email alex.green@benifex.com already exists", e.getMessage());
	}

	@Test
	void shouldThrowExceptionWhenFindingEmployeeRecordWithNonExistingEmployeeNo() {
		when(employeeRepository.existsById(anyString())).thenReturn(false);

		Exception e = assertThrows(EmployeeNotFoundException.class, () -> employeeService.findEmployeeByEmployeeNo("123"));
		assertEquals("Employee not found with employee number: 123", e.getMessage());
	}

	@Test
	void shouldThrowExceptionWhenDeletingEmployeeRecordWithNonExistingEmployeeNo() {
		when(employeeRepository.existsById(anyString())).thenReturn(false);

		Exception e = assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteEmployeeByEmployeeNo("123"));
		assertEquals("Employee not found with employee number: 123", e.getMessage());
	}

	private Employee generateEmployee() {
		LocalDate dob = LocalDate.of(1989, 10, 16);

		Employee employee = new Employee();
		employee.setEmployeeNo(EMPLOYEE_NUMBER_PREFIX + "123");
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
