package com.alex.green.benifex.test.employee_service.service;

import com.alex.green.benifex.test.employee_service.dto.response.EmployeeResponse;
import com.alex.green.benifex.test.employee_service.dto.transfer.EmployeeTransferDTO;
import com.alex.green.benifex.test.employee_service.exception.DuplicateEmailAddressException;
import com.alex.green.benifex.test.employee_service.exception.EmployeeNotFoundException;
import com.alex.green.benifex.test.employee_service.model.Employee;
import com.alex.green.benifex.test.employee_service.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.alex.green.benifex.test.employee_service.integration.utils.TestDataUtils.generateEmployee;
import static com.alex.green.benifex.test.employee_service.integration.utils.TestDataUtils.generateEmployeeTransferDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.AssertionsKt.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTests {

	private static final String EMPLOYEE_NUMBER_PREFIX =  "BENIFEX-EMP-";

	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private EmployeeService employeeService;

	@Test
	void shouldSuccessfullyStoreEmployeeRecord() {
		Employee newEmployee = generateEmployee();
		EmployeeTransferDTO newEmployeeDto = generateEmployeeTransferDto();

		when(employeeRepository.existsByEmail("alex.green@benifex.com")).thenReturn(false);
		when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));

		EmployeeResponse savedEmployee = employeeService.createEmployee(newEmployeeDto);

		assertNotNull(savedEmployee.getEmployeeNo());
		assertTrue(savedEmployee.getEmployeeNo().startsWith(EMPLOYEE_NUMBER_PREFIX));

		verify(employeeRepository, times(1)).existsByEmail(newEmployee.getEmail());
		verify(employeeRepository, times(1)).save(any(Employee.class));
	}

	@Test
	void shouldThrowExceptionWhenCreatingEmployeeRecordWithExistingEmail() {
		when(employeeRepository.existsByEmail("alex.green@benifex.com")).thenReturn(true);

		Exception e = assertThrows(DuplicateEmailAddressException.class, () -> employeeService.createEmployee(generateEmployeeTransferDto()));
		assertEquals("An employee with email alex.green@benifex.com already exists", e.getMessage());
	}

	@Test
	void shouldThrowExceptionWhenFindingEmployeeRecordWithNonExistingEmployeeNo() {
		Exception e = assertThrows(EmployeeNotFoundException.class, () -> employeeService.findEmployeeByEmployeeNo("123"));
		assertEquals("Employee not found with employee number: 123", e.getMessage());
	}

	@Test
	void shouldThrowExceptionWhenDeletingEmployeeRecordWithNonExistingEmployeeNo() {
		when(employeeRepository.existsById(anyString())).thenReturn(false);

		Exception e = assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteEmployeeByEmployeeNo("123"));
		assertEquals("Employee not found with employee number: 123", e.getMessage());
	}
}
