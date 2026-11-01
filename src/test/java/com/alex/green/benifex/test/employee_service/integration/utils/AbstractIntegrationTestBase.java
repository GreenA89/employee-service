package com.alex.green.benifex.test.employee_service.integration.utils;

import com.alex.green.benifex.test.employee_service.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mongodb.MongoDBContainer;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
public abstract class AbstractIntegrationTestBase {

	protected static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:8.0");

	@Autowired
	protected TestRestTemplate restTemplate;

	@Autowired
	protected EmployeeRepository employeeRepository;

	@Autowired
	protected ObjectMapper objectMapper;

	@DynamicPropertySource
	static void mongoProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	static {
		mongoDBContainer.start();
	}

	@BeforeEach
	void setUp() {
		employeeRepository.deleteAll();
	}
}
