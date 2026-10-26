package com.alex.green.benifex.test.employee_service.utils;

import java.util.UUID;

public class EmployeeUtils {

	public static String createEmployeeNumber() {
		return "BENIFEX-EMP-" + UUID.randomUUID();
	}

}
