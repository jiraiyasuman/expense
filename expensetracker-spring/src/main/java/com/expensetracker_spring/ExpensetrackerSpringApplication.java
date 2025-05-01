package com.expensetracker_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ExpensetrackerSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpensetrackerSpringApplication.class, args);
	}

}
