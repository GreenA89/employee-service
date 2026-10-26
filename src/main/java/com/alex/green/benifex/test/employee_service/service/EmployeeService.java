package com.alex.green.benifex.test.employee_service.service;

import com.alex.green.benifex.test.employee_service.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;

	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
}
