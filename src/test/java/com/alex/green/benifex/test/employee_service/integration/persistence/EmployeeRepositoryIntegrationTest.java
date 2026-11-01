package com.alex.green.benifex.test.employee_service.integration.persistence;

import com.alex.green.benifex.test.employee_service.integration.utils.AbstractIntegrationTestBase;
import com.alex.green.benifex.test.employee_service.model.Employee;
import com.alex.green.benifex.test.employee_service.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.IndexInfo;

import java.util.List;

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

		assertThrows(DuplicateKeyException.class, () -> employeeRepository.save(employeeWithDuplicateEmail));
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
