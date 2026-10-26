package com.alex.green.benifex.test.employee_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "employees")
public class Employee {

	@Id
	private String employeeNo;
	private String title;
	private String firstName;
	private String surname;
	private Date dob;
	private String gender;
	private String address;

}
