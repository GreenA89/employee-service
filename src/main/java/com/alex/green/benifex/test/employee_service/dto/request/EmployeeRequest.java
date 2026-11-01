package com.alex.green.benifex.test.employee_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public class EmployeeRequest {

	@NotBlank
	private String title;

	@NotBlank
	private String firstName;

	@NotBlank
	private String surname;

	@NotNull
	@Past
	private LocalDate dob;

	@NotBlank
	private String gender;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String address;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	@Override
	public String toString() {
		return "Employee{" +
				", title='" + title + '\'' +
				", firstName='" + firstName + '\'' +
				", surname='" + surname + '\'' +
				", dob=" + dob +
				", gender='" + gender + '\'' +
				", email='" + email + '\'' +
				", address='" + address + '\'' +
				'}';
	}
}
