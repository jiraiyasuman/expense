package com.expensetracker_spring.integration;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;


public class AbstractContainerBaseTest {

	static final MySQLContainer MY_SQL_CONTAINER ;
	static {
		MY_SQL_CONTAINER = new MySQLContainer("mysql:latest")
				.withUsername("root")
				.withPassword("12345")
				.withDatabaseName("expense");
		MY_SQL_CONTAINER.start();
	}
	@DynamicPropertySource
	public static void dynamicPropertySource(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
		dynamicPropertyRegistry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
		dynamicPropertyRegistry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
	}
}
