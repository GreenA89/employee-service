package com.alex.green.benifex.test.employee_service.service;

import com.alex.green.benifex.test.employee_service.model.Employee;
import com.alex.green.benifex.test.employee_service.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

	private static final Logger LOG = LoggerFactory.getLogger(EmployeeService.class);

	private final EmployeeRepository employeeRepository;

	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public Employee createEmployee(Employee employee) {
		String employeeNumber = createEmployeeNumber();
		employee.setEmployeeNo(employeeNumber);

		LOG.info("Creating employee with id: {}", employeeNumber);
		return employeeRepository.save(employee);
	}

	public Employee findEmployeeByEmployeeNo(String employeeNo) throws Exception {
		Employee foundEmployee = employeeRepository.findById(employeeNo).orElse(null);
		if (foundEmployee == null) {
			LOG.error("Employee with employeeNo {} not found", employeeNo);
			throw new Exception("Employee with employeeNo " + employeeNo + " not found");
		}
		return foundEmployee;
	}

	public List<Employee> findAllEmployees() throws Exception {
		return employeeRepository.findAll();
	}

	private String createEmployeeNumber() {
		return "BENIFEX-EMP-" + UUID.randomUUID();
	}
}
