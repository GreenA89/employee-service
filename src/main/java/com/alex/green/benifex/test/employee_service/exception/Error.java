package com.alex.green.benifex.test.employee_service.exception;

import java.time.Instant;

public record Error(
	Instant timestamp,
	int status,
	String error,
	String message,
	String path
) {}
