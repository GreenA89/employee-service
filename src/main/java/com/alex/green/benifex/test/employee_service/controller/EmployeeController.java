package com.alex.green.benifex.test.employee_service.controller;

import com.alex.green.benifex.test.employee_service.model.Employee;
import com.alex.green.benifex.test.employee_service.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping
	public List<Employee> findAllEmployees() throws Exception {
		LOG.info("/employee GET called");
		return employeeService.findAllEmployees();
	}

	@GetMapping("/{employeeNo}")
	public Employee findEmployee(@PathVariable String employeeNo) throws Exception {
		LOG.info("/employee/{employeeNo} GET called with: {}", employeeNo);
		return employeeService.findEmployeeByEmployeeNo(employeeNo);
	}

	@PostMapping
	public Employee createEmployee(@RequestBody Employee employee) {
		LOG.info("/employee POST called with: {}", employee);
		Employee createdEmployee = employeeService.createEmployee(employee);
		return createdEmployee;
	}
}
