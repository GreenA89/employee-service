package com.alex.green.benifex.test.employee_service.integration.persistence;

import com.alex.green.benifex.test.employee_service.integration.utils.AbstractIntegrationTestBase;
import com.alex.green.benifex.test.employee_service.model.Employee;
import com.alex.green.benifex.test.employee_service.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.IndexInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import static com.alex.green.benifex.test.employee_service.integration.utils.TestDataUtils.generateEmployee;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeeRepositoryIntegrationTest extends AbstractIntegrationTestBase {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Test
	void shouldEnforceUniqueEmailException() {
		// GIVEN a new employee request
		Employee newEmployee = generateEmployee();
		newEmployee.setEmployeeNo("BENIFEX-EMP-123");
		employeeRepository.save(newEmployee);

		// WHEN another employee record is saved with a duplicate email address
		Employee employeeWithDuplicateEmail = generateEmployee();
		newEmployee.setEmployeeNo("BENIFEX-EMP-456");

		// THEN a duplicate key exception is thrown
		assertThrows(DuplicateKeyException.class, () -> employeeRepository.save(employeeWithDuplicateEmail));
	}

	@Test
	void shouldPersistEmployeeRecordAndFindById() {
		// GIVEN a new employee request
		Employee newEmployee = generateEmployee();
		newEmployee.setEmployeeNo("BENIFEX-EMP-123");

		// WHEN the employee record is saved
		employeeRepository.save(newEmployee);

		// THEN the employee record is stored in the database persistence
		Optional<Employee> optionalSavedEmployee = employeeRepository.findById("BENIFEX-EMP-123");
		Employee savedEmployee = optionalSavedEmployee.orElse(null);

		assertNotNull(savedEmployee);
		assertEquals(savedEmployee.getEmployeeNo(), newEmployee.getEmployeeNo());
	}

	@Test
	void shouldPersistEmployeeRecordAndFindByEmail() {
		// GIVEN a new employee request
		Employee newEmployee = generateEmployee();
		newEmployee.setEmployeeNo("BENIFEX-EMP-123");

		// WHEN the employee record is saved
		employeeRepository.save(newEmployee);

		// THEN the employee record is stored in the database persistence
		Optional<Employee> optionalSavedEmployee = employeeRepository.findByEmail("alex.green@benifex.com");
		Employee savedEmployee = optionalSavedEmployee.orElse(null);

		assertNotNull(savedEmployee);
		assertEquals(savedEmployee.getEmployeeNo(), newEmployee.getEmployeeNo());
	}

	@Test
	void shouldReturnFullListOfSavedEmployees() {
		// GIVEN two new employee requests
		Employee newEmployeeOne = generateEmployee();
		newEmployeeOne.setEmployeeNo("BENIFEX-EMP-123");

		Employee newEmployeeTwo = generateEmployee();
		newEmployeeTwo.setEmployeeNo("BENIFEX-EMP-345");
		newEmployeeTwo.setEmail("alex.blue@benifex.com");

		// WHEN the employee records are saved
		employeeRepository.save(newEmployeeOne);
		employeeRepository.save(newEmployeeTwo);

		// THEN the employee record is stored in the database persistence
		List<Employee> employeeList = employeeRepository.findAll();
		assertEquals(2, employeeList.size());
	}

	@Test
	void shouldDeleteEmployeeRecord() {
		// GIVEN a new employee request
		Employee newEmployee = generateEmployee();
		newEmployee.setEmployeeNo("BENIFEX-EMP-123");

		// AND the employee record is saved
		employeeRepository.save(newEmployee);
		Optional<Employee> optionalSavedEmployee = employeeRepository.findById("BENIFEX-EMP-123");
		Employee savedEmployee = optionalSavedEmployee.orElse(newEmployee);
		assertNotNull(savedEmployee);
		assertEquals(savedEmployee.getEmployeeNo(), newEmployee.getEmployeeNo());

		// WHEN the employee record is deleted
		employeeRepository.deleteById("BENIFEX-EMP-123");

		// THEN the employee record is no longer present in persistence
		Optional<Employee> optionalDeletedEmployee = employeeRepository.findById("BENIFEX-EMP-123");
		assertTrue(optionalDeletedEmployee.isEmpty());
	}

	@Test
	void indexesShouldExist() {
		List<IndexInfo> indexes = mongoTemplate.indexOps(Employee.class).getIndexInfo();

		assertTrue(indexes.stream()
				.anyMatch(index ->
						index.isUnique() &&
						index.getIndexFields().stream().anyMatch(field -> field.getKey().equals("email"))
		));
	}


}
