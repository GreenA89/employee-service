package com.alex.green.benifex.test.employee_service.integration.employee;

import com.alex.green.benifex.test.employee_service.dto.request.EmployeeRequest;
import com.alex.green.benifex.test.employee_service.dto.response.EmployeeResponse;
import com.alex.green.benifex.test.employee_service.integration.utils.AbstractIntegrationTestBase;
import com.alex.green.benifex.test.employee_service.model.Employee;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tools.jackson.databind.JsonNode;

import java.time.LocalDate;
import java.util.stream.Stream;

import static com.alex.green.benifex.test.employee_service.integration.utils.TestDataUtils.generateEmployee;
import static com.alex.green.benifex.test.employee_service.integration.utils.TestDataUtils.generateEmployeeRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeeIntegrationTest extends AbstractIntegrationTestBase {

	@Test
	void shouldSuccessfullyStoreEmployee() {
		// GIVEN a new employee request
		EmployeeRequest newEmployee = generateEmployeeRequest();

		// WHEN the employee record is successfully created
		ResponseEntity<@NotNull EmployeeResponse> savedEmployeeResponse = restTemplate.postForEntity("/employee", newEmployee, EmployeeResponse.class);
		assertNotNull(savedEmployeeResponse.getBody());
		assertEquals(HttpStatus.CREATED, savedEmployeeResponse.getStatusCode());

		// THEN an employee number has been generated
		EmployeeResponse savedEmployee = savedEmployeeResponse.getBody();
		assertNotNull(savedEmployee.getEmployeeNo());

		// AND the persisted employee in the database can be retrieved with this generated employee number
		ResponseEntity<@NotNull EmployeeResponse> retrievedEmployeeResponse = restTemplate.getForEntity("/employee/{employeeNo}", EmployeeResponse.class, savedEmployee.getEmployeeNo());
		assertNotNull(retrievedEmployeeResponse.getBody());
		assertEquals(HttpStatus.OK, retrievedEmployeeResponse.getStatusCode());
	}

	@ParameterizedTest
	@MethodSource("provideDuplicateEmailTestValues")
	void shouldNotStoreEmployeeRecordWithDuplicateEmailAddress(String duplicateEmailAddress) {
		// GIVEN two new employee requests with the same email address
		EmployeeRequest newEmployeeOne = generateEmployeeRequest();
		EmployeeRequest newEmployeeTwo = generateEmployeeRequest();

		// WHEN the employee one record is successfully created
		ResponseEntity<@NotNull EmployeeResponse> savedEmployeeOneResponse = restTemplate.postForEntity("/employee", newEmployeeOne, EmployeeResponse.class);
		assertEquals(HttpStatus.CREATED, savedEmployeeOneResponse.getStatusCode());

		// AND an attempt is made to create a record for employee two with a duplicate email address
		// THEN a duplicate email exception is thrown
		newEmployeeTwo.setEmail(duplicateEmailAddress);
		ResponseEntity<@NotNull String> savedEmployeeTwoResponse = restTemplate.postForEntity("/employee", newEmployeeTwo, String.class);
		assertEquals(HttpStatus.CONFLICT, savedEmployeeTwoResponse.getStatusCode());

		JsonNode responseBody = objectMapper.readTree(savedEmployeeTwoResponse.getBody());
		assertEquals("An employee with email " + duplicateEmailAddress + " already exists", responseBody.get("message").asString());
	}

	@Test
	void shouldNotStoreEmployeeWithInvalidFieldValue() {
		// GIVEN a new employee request with an invalid email and invalid date of birth
		EmployeeRequest newEmployeeWithInvalidDetails = generateEmployeeRequest();
		newEmployeeWithInvalidDetails.setEmail("invalidEmail");
		newEmployeeWithInvalidDetails.setDob(LocalDate.now());

		// WHEN an attempt is made to create the new employee record
		ResponseEntity<@NotNull String> savedEmployeeResponse = restTemplate.postForEntity("/employee", newEmployeeWithInvalidDetails, String.class);

		// Then a bad request response is returned along with the appropriate error messages for the client
		assertEquals(HttpStatus.BAD_REQUEST, savedEmployeeResponse.getStatusCode());
		JsonNode responseBody = objectMapper.readTree(savedEmployeeResponse.getBody());
		assertTrue(responseBody.get("message").asString().contains("email: must be a well-formed email address"));
		assertTrue(responseBody.get("message").asString().contains("dob: must be a past date"));
	}

	static Stream<Arguments> provideDuplicateEmailTestValues() {
		return Stream.of(
				Arguments.of("alex.green@benifex.com", "alex.green@benifex.com"),
				Arguments.of("alex.green@benifex.com", "ALEX.GREEN@BENIFEX.COM"),
				Arguments.of("ALEX.GREEN@BENIFEX.com", "alex.green@benifex.com"),
				Arguments.of("ALEX.GREEN@BENIFEX.com", "ALEX.GREEN@BENIFEX.COM")
		);
	}
}
