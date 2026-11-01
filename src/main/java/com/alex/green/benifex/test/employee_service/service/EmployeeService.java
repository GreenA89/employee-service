package com.alex.green.benifex.test.employee_service.service;

import com.alex.green.benifex.test.employee_service.dto.response.EmployeeResponse;
import com.alex.green.benifex.test.employee_service.dto.transfer.EmployeeTransferDTO;
import com.alex.green.benifex.test.employee_service.exception.DuplicateEmailAddressException;
import com.alex.green.benifex.test.employee_service.exception.EmployeeNotFoundException;
import com.alex.green.benifex.test.employee_service.model.Employee;
import com.alex.green.benifex.test.employee_service.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

import static com.alex.green.benifex.test.employee_service.utils.EmployeeConverter.convert;
import static com.alex.green.benifex.test.employee_service.utils.EmployeeUtils.createEmployeeNumber;

@Service
public class EmployeeService {

	private static final Logger LOG = LoggerFactory.getLogger(EmployeeService.class);

	private final EmployeeRepository employeeRepository;

	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public EmployeeResponse createEmployee(EmployeeTransferDTO employee) {
		String employeeNumber = createEmployeeNumber();
		employee.setEmployeeNo(employeeNumber);

		if (employeeRepository.existsByEmail(employee.getEmail().toLowerCase(Locale.ROOT))) {
			LOG.warn("Attempted to create employee with duplicate email: {}", employee.getEmail());
			throw new DuplicateEmailAddressException(employee.getEmail());
		}

		LOG.info("Creating employee with id: {}", employeeNumber);
		Employee savedEmployee = employeeRepository.save(convert(employee));
		return convert(savedEmployee);
	}

	public EmployeeResponse findEmployeeByEmployeeNo(String employeeNo) {
		Employee employee = employeeRepository.findById(employeeNo).orElse(null);
		if (employee == null) {
			LOG.warn("Employee with employeeNo {} not found", employeeNo);
			throw new EmployeeNotFoundException(employeeNo);
		}
		return convert(employee);
	}

	public List<EmployeeResponse> findAllEmployees() {
		return convert(employeeRepository.findAll());
	}

	public void deleteEmployeeByEmployeeNo(String employeeNo) {
		try {
			employeeRepository.deleteById(employeeNo);
		} catch (OptimisticLockingFailureException e) {
			LOG.warn("Employee with employeeNo {} not found", employeeNo);
			throw new EmployeeNotFoundException(employeeNo);
		}
	}
}
