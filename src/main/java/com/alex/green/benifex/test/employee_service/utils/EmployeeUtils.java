package com.alex.green.benifex.test.employee_service.utils;

import java.util.UUID;
import java.util.Locale;

public class EmployeeUtils {

	private static final String EMPLOYEE_NUMBER_PREFIX =  "BENIFEX-EMP-";

	public static String createEmployeeNumber() {
		return EMPLOYEE_NUMBER_PREFIX + UUID.randomUUID();
	}

}
