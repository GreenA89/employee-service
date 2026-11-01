package com.alex.green.benifex.test.employee_service.controller;

import com.alex.green.benifex.test.employee_service.dto.request.EmployeeRequest;
import com.alex.green.benifex.test.employee_service.dto.response.EmployeeResponse;
import com.alex.green.benifex.test.employee_service.dto.transfer.EmployeeTransferDTO;
import com.alex.green.benifex.test.employee_service.model.Employee;
import com.alex.green.benifex.test.employee_service.service.EmployeeService;

import static com.alex.green.benifex.test.employee_service.integration.utils.TestDataUtils.generateEmployee;
import static com.alex.green.benifex.test.employee_service.integration.utils.TestDataUtils.generateEmployeeRequest;
import static com.alex.green.benifex.test.employee_service.integration.utils.TestDataUtils.generateEmployeeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;

import static com.alex.green.benifex.test.employee_service.utils.EmployeeConverter.convert;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private EmployeeService employeeService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void shouldReturnValidationErrorsWhenEmployeeIsContainsBlankOrNullValues() throws Exception {
		Employee employee = generateEmployee();
		employee.setFirstName("");
		employee.setEmail("");
		employee.setSurname("");
		employee.setDob(null);
		employee.setGender("");
		employee.setTitle("");
		employee.setAddress("");

		mockMvc.perform(post("/employee")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(employee)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.message", containsString("firstName: must not be blank")))
				.andExpect(jsonPath("$.message", containsString("email: must not be blank")))
				.andExpect(jsonPath("$.message", containsString("surname: must not be blank")))
				.andExpect(jsonPath("$.message", containsString("dob: must not be null")))
				.andExpect(jsonPath("$.message", containsString("address: must not be blank")))
				.andExpect(jsonPath("$.message", containsString("gender: must not be blank")))
				.andExpect(jsonPath("$.message", containsString("title: must not be blank")));

		verify(employeeService, never()).createEmployee(any(EmployeeTransferDTO.class));
	}

	@Test
	void shouldReturnValidationErrorsWhenEmployeeIsContainsInvalidValues() throws Exception {
		Employee employee = generateEmployee();
		employee.setEmail("invalid");
		employee.setDob(LocalDate.now());

		mockMvc.perform(post("/employee")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(employee)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.message", containsString("email: must be a well-formed email address")))
				.andExpect(jsonPath("$.message", containsString("dob: must be a past date")));

		verify(employeeService, never()).createEmployee(any(EmployeeTransferDTO.class));
	}

	@Test
	void findAllEmployees() throws Exception {
		List<EmployeeResponse> employees = convert(List.of(generateEmployee()));
		when(employeeService.findAllEmployees()).thenReturn(employees);

		mockMvc.perform(get("/employee"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].employeeNo").value("BENIFEX-EMP-123"))
				.andExpect(jsonPath("$[0].firstName").value("Alex"))
				.andExpect(jsonPath("$[0].surname").value("Green"))
				.andExpect(jsonPath("$[0].dob").value("1989-10-16"))
				.andExpect(jsonPath("$[0].gender").value("Male"))
				.andExpect(jsonPath("$[0].title").value("Sir"))
				.andExpect(jsonPath("$[0].address").value("123 Apple Street"))
				.andExpect(jsonPath("$[0].email").value("alex.green@benifex.com"));

		verify(employeeService, times(1)).findAllEmployees();
	}

	@Test
	void findEmployee() throws Exception {
		EmployeeResponse newEmployee = convert(generateEmployee());

		when(employeeService.findEmployeeByEmployeeNo(newEmployee.getEmployeeNo())).thenReturn(newEmployee);

		mockMvc.perform(get("/employee/{employeeNo}", newEmployee.getEmployeeNo()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.employeeNo").value("BENIFEX-EMP-123"))
				.andExpect(jsonPath("$.firstName").value("Alex"))
				.andExpect(jsonPath("$.surname").value("Green"))
				.andExpect(jsonPath("$.dob").value("1989-10-16"))
				.andExpect(jsonPath("$.gender").value("Male"))
				.andExpect(jsonPath("$.title").value("Sir"))
				.andExpect(jsonPath("$.address").value("123 Apple Street"))
				.andExpect(jsonPath("$.email").value("alex.green@benifex.com"));

		verify(employeeService, times(1)).findEmployeeByEmployeeNo(newEmployee.getEmployeeNo());
	}

	@Test
	void createEmployee() throws Exception {
		EmployeeRequest employeeRequest = generateEmployeeRequest();
		EmployeeResponse newEmployeeResponse = generateEmployeeResponse();

		when(employeeService.createEmployee(any(EmployeeTransferDTO.class))).thenReturn(newEmployeeResponse);

		mockMvc.perform(post("/employee")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(employeeRequest)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.employeeNo").value("BENIFEX-EMP-123"))
				.andExpect(jsonPath("$.firstName").value("Alex"))
				.andExpect(jsonPath("$.surname").value("Green"))
				.andExpect(jsonPath("$.dob").value("1989-10-16"))
				.andExpect(jsonPath("$.gender").value("Male"))
				.andExpect(jsonPath("$.title").value("Sir"))
				.andExpect(jsonPath("$.address").value("123 Apple Street"))
				.andExpect(jsonPath("$.email").value("alex.green@benifex.com"))
				.andExpect(header().exists(HttpHeaders.LOCATION))
				.andExpect(header().string(HttpHeaders.LOCATION, endsWith("/employee/BENIFEX-EMP-123")));

		verify(employeeService, times(1)).createEmployee(any(EmployeeTransferDTO.class));
	}

	@Test
	void deleteEmployee() throws Exception {
		String employeeNo = "BENIFEX-EMP-123";

		mockMvc.perform(delete("/employee/{employeeNo}", employeeNo))
				.andExpect(status().isNoContent());

		verify(employeeService, times(1)).deleteEmployeeByEmployeeNo(employeeNo);
	}
}