package com.alex.green.benifex.test.employee_service.repository;

import com.alex.green.benifex.test.employee_service.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

	Optional<Employee> findByEmail(String name);

	boolean existsByEmail(String email);

}
