package com.alex.green.benifex.test.employee_service.integration;

import com.alex.green.benifex.test.employee_service.integration.utils.AbstractIntegrationTestBase;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppStartUpIntegrationTest extends AbstractIntegrationTestBase {

	@Test
	public void applicationStartsSuccessfully() {
		assertTrue(mongoDBContainer.isRunning());

		ResponseEntity<@NotNull String> response = restTemplate.getForEntity("/healthcheck", String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("OK", response.getBody());
	}
}
