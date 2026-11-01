package com.alex.green.benifex.test.employee_service.controller;

import com.alex.green.benifex.test.employee_service.dto.request.EmployeeRequest;
import com.alex.green.benifex.test.employee_service.dto.response.EmployeeResponse;
import com.alex.green.benifex.test.employee_service.service.EmployeeService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.alex.green.benifex.test.employee_service.utils.EmployeeConverter.convert;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping
	public List<EmployeeResponse> findAllEmployees() {
		LOG.info("/employee GET called");
		return employeeService.findAllEmployees();
	}

	@GetMapping("/{employeeNo}")
	public EmployeeResponse findEmployee(@PathVariable String employeeNo) {
		LOG.info("/employee/{employeeNo} GET called with: {}", employeeNo);
		return employeeService.findEmployeeByEmployeeNo(employeeNo);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EmployeeResponse createEmployee(@RequestBody @Valid EmployeeRequest employee, HttpServletResponse response) {
		LOG.info("/employee POST called with: {}", employee);
		EmployeeResponse createdEmployee = employeeService.createEmployee(convert(employee));

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{employeeNo}")
				.buildAndExpand(createdEmployee.getEmployeeNo())
				.toUri();
		response.setHeader(HttpHeaders.LOCATION, location.toString());

		return createdEmployee;
	}

	@DeleteMapping("/{employeeNo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteEmployee(@PathVariable String employeeNo) {
		LOG.info("/employee DELETE called with: {}", employeeNo);
		employeeService.deleteEmployeeByEmployeeNo(employeeNo);
	}
}
