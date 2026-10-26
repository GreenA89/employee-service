package com.alex.green.benifex.test.employee_service.utils;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeUtilsTest {

	private static final String EMPLOYEE_NUMBER_PREFIX =  "BENIFEX-EMP-";

	@Test
	void shouldCreateEmployeeNumberInAppropriateFormat() {
		String employeeNumber = EmployeeUtils.createEmployeeNumber();
		assertNotNull(employeeNumber);
		assertThat(employeeNumber, containsString(EMPLOYEE_NUMBER_PREFIX));
	}
}