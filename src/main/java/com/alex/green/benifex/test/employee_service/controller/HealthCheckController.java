package com.alex.green.benifex.test.employee_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthcheck")
public class HealthCheckController {

	private static final Logger LOG = LoggerFactory.getLogger(HealthCheckController.class);

	@GetMapping
	public String healthCheck() {
		LOG.info("Health check called");
		return "OK";
	}

}
