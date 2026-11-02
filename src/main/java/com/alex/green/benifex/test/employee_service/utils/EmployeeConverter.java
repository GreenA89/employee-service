package com.alex.green.benifex.test.employee_service.utils;

import com.alex.green.benifex.test.employee_service.dto.request.EmployeeRequest;
import com.alex.green.benifex.test.employee_service.dto.response.EmployeeResponse;
import com.alex.green.benifex.test.employee_service.dto.transfer.EmployeeTransferDTO;
import com.alex.green.benifex.test.employee_service.model.Employee;

import java.util.ArrayList;
import java.util.List;

import static com.alex.green.benifex.test.employee_service.utils.EmployeeUtils.createEmployeeNumber;

public class EmployeeConverter {

	public static EmployeeTransferDTO convert(EmployeeRequest employee) {
		EmployeeTransferDTO employeeTransferDto = new EmployeeTransferDTO();
		employeeTransferDto.setEmployeeNo(createEmployeeNumber());
		employeeTransferDto.setFirstName(employee.getFirstName());
		employeeTransferDto.setSurname(employee.getSurname());
		employeeTransferDto.setEmail(employee.getEmail());
		employeeTransferDto.setTitle(employee.getTitle());
		employeeTransferDto.setDob(employee.getDob());
		employeeTransferDto.setGender(employee.getGender());
		employeeTransferDto.setAddress(employee.getAddress());
		return employeeTransferDto;
	}

	public static Employee convert(EmployeeTransferDTO employeeTransferDto) {
		Employee employee = new Employee();
		employee.setEmployeeNo(employeeTransferDto.getEmployeeNo());
		employee.setFirstName(employeeTransferDto.getFirstName());
		employee.setSurname(employeeTransferDto.getSurname());
		employee.setEmail(employeeTransferDto.getEmail().toLowerCase());
		employee.setTitle(employeeTransferDto.getTitle());
		employee.setDob(employeeTransferDto.getDob());
		employee.setGender(employeeTransferDto.getGender());
		employee.setAddress(employeeTransferDto.getAddress());
		return employee;
	}

	public static EmployeeResponse convert(Employee employee) {
		EmployeeResponse employeeResponse = new EmployeeResponse();
		employeeResponse.setEmployeeNo(employee.getEmployeeNo());
		employeeResponse.setFirstName(employee.getFirstName());
		employeeResponse.setSurname(employee.getSurname());
		employeeResponse.setEmail(employee.getEmail());
		employeeResponse.setTitle(employee.getTitle());
		employeeResponse.setDob(employee.getDob());
		employeeResponse.setGender(employee.getGender());
		employeeResponse.setAddress(employee.getAddress());
		return employeeResponse;
	}

	public static List<EmployeeResponse> convert(List<Employee> employees) {
		List<EmployeeResponse> employeeResponseList = new ArrayList<>();
		for (Employee employee : employees) {
			EmployeeResponse employeeResponse = new EmployeeResponse();
			employeeResponse.setEmployeeNo(employee.getEmployeeNo());
			employeeResponse.setFirstName(employee.getFirstName());
			employeeResponse.setSurname(employee.getSurname());
			employeeResponse.setEmail(employee.getEmail());
			employeeResponse.setTitle(employee.getTitle());
			employeeResponse.setDob(employee.getDob());
			employeeResponse.setGender(employee.getGender());
			employeeResponse.setAddress(employee.getAddress());
			employeeResponseList.add(employeeResponse);
		}
		return employeeResponseList;
	}
}
