package com.expensetracker_spring.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.expensetracker_spring.dto.ExpenseDto;
import com.expensetracker_spring.entity.Expense;
import com.expensetracker_spring.mapper.ExpenseMapper;
import com.expensetracker_spring.repository.ExpenseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerITests {

	private MockMvc mockMvc;
	private ExpenseRepository expenseRepository;
	private ObjectMapper objectMapper;
	@Autowired
	public EmployeeControllerITests(MockMvc mockMvc, ExpenseRepository expenseRepository, ObjectMapper objectMapper) {
		super();
		this.mockMvc = mockMvc;
		this.expenseRepository = expenseRepository;
		this.objectMapper = objectMapper;
	}
	private ExpenseDto expenseDto;
	private ExpenseMapper expenseMapper;
	@BeforeEach
	void setUp() {
		expenseRepository.deleteAll();
	}
	@Test
	public void givenExpenseObject_whenCreateExpense_thenReturnSavedExpense200()throws Exception{
		// given - pre condition or setup
				Expense expense = Expense.builder()
						.expenseName("Team Lunch")
						.amount(1560.99)
						.date("2025-04-30")
						.build();
				// when - action or behaviour that we are going test
				ResultActions response = mockMvc.perform(
					    post("/expense/save")
					        .contentType(MediaType.APPLICATION_JSON)
					        .content(objectMapper.writeValueAsString(expense))
					);
				
				// then - verify the result or output using assert statements
				response.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.expenseName",is(expense.getExpenseName())))
				.andExpect(jsonPath("$.amount", is(expense.getAmount())))
				.andExpect(jsonPath("$.date",is(expense.getDate())));			
	}
	// Junit test case for get All expense Rest API
	@Test
	public void givenListOfExpense_whengetAllExpense_thenReturnExpenseAll()throws Exception{
		// given - precondition of setup
				List<Expense> listOfExpense = new ArrayList<>();
				Expense expense = Expense.builder()
						.expenseName("Team Lunch")
						.amount(1560.99)
						.date("2025-04-30")
						.build();
				Expense expense1 = Expense.builder()
						.expenseName("BreakFast")
						.amount(120.00)
						.date("2025-05-01")
						.build();
			 
				ExpenseDto expenseDto = expenseMapper.mapToExpenseDto(expense);
				ExpenseDto expenseDto1 = expenseMapper.mapToExpenseDto(expense1);
				
				listOfExpense.add(expense);
				listOfExpense.add(expense1);
				
				expenseRepository.saveAll(listOfExpense);
				// when - action or the behaviour that we are going test
				ResultActions response = mockMvc.perform(get("/expense/list"));
				// then - verify the output
				response.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.size()",
						is(listOfExpense.size())));
	}
	// positive scenario - valid expense id
	// JUnit test for GET expense by id REST API
	@Test
	public void givenExpenseById_whenExpenseById_thenReturnExpenseObject()throws Exception{
		// given - precondition or setup
				Expense expense = Expense.builder()
						.expenseName("Office Lunch")
						.amount(1000)
						.date("2025-04-30").build();
				expenseRepository.save(expense);
			    // when - action or the behaviour that we are going test
				ResultActions response = mockMvc.perform(get("/expense/list/{id}"));
				// then - verify he output
				response.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.expenseName",is(expense.getExpenseName())))
				.andExpect(jsonPath("$.amount",is(expense.getAmount())))
				.andExpect(jsonPath("$.date",is(expense.getDate())));
			
	}
	// negative scenario - valid expense id
	// Junit test for GET expense by id REST API
	@Test
	public void givenInvalidExpenseId_whenGetExpenseById_thenReturnExpenseObject404()throws Exception{
		// given - precondition or setup
				int expenseId = 1;
				Expense expense = Expense.builder()
						.expenseName("Team Lunch")
						.amount(1000)
						.date("2025-04-25")
						.build();
				expenseRepository.save(expense);
				// when - action or the behaviour that we are going test
				ResultActions response = mockMvc.perform(get("/expense/list/{id}"));
				// then verify the output
				response.andExpect(status().isNotFound())
				.andDo(print());
			
		
		
		
	}
	// Junit test for update expense REST API - positive scenario
	@Test
	public void givenUpdateExpense_whenUpdatedExpense_thenReturnExpense200()throws Exception
	{
		Expense expense = Expense.builder()
				.expenseName("Team Lunch")
				.amount(1000)
				.date("2025-04-25")
				.build();
		expenseRepository.save(expense);
		Expense updatedExpense = Expense.builder()
				.expenseName("Client Lunch")
				.amount(1500)
				.date("2025-05-01")
				.build();
		
		// when - action or behaviour that we are going test
		ResultActions response = mockMvc.perform(
			    put("/expense/update/{id}", 1)
			        .contentType(MediaType.APPLICATION_JSON)
			        .content(objectMapper.writeValueAsString(updatedExpense))
			);
		// then - verify the output
		response.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$.expenseName",is(updatedExpense.getExpenseName())))
		.andExpect(jsonPath("$.amount", is(updatedExpense.getAmount())))
		.andExpect(jsonPath("$.date",is(updatedExpense.getDate())));
	
	}
	//Junit test for update expense - negative scenario
	public void givenUpdatedExpense_whenUpdatedExpense_thenReturn404()throws Exception {
		Expense expense = Expense.builder()
				.expenseName("Team Lunch")
				.amount(1000)
				.date("2025-04-25")
				.build();
		expenseRepository.save(expense);
		Expense updatedExpense = Expense.builder()
				.expenseName("Client Lunch")
				.amount(1500)
				.date("2025-05-01")
				.build();
		// when - action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(
			    put("/expense/update/{id}", 1)
			        .contentType(MediaType.APPLICATION_JSON)
			        .content(objectMapper.writeValueAsString(updatedExpense))
			);
		// then - verify the output
		response.andExpect(status().isNotFound())
		.andDo(print());	
	}
	// Junit test for delete expense REST API
	@Test
	public void givenExpenseId_whenDeleteExpense_thenReturn200()throws Exception{
		Expense expense = Expense.builder()
				.expenseName("Team Lunch")
				.amount(1000)
				.date("2025-04-25")
				.build();
		expenseRepository.save(expense);
		// when - action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(delete("/expense/delete/{id}"));
		// then - verify the output
		response.andExpect(status().isOk())
		.andDo(print());
	}
	
	
	
}
