package com.expensetracker_spring.controller;

import com.expensetracker_spring.dto.ExpenseDto;
import com.expensetracker_spring.entity.Expense;
import com.expensetracker_spring.mapper.ExpenseMapper;
import com.expensetracker_spring.service.ExpenseService;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
public class ExpenseControllerTest {

	
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	@Autowired
	public ExpenseControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
		super();
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
	}
	@MockBean
	private ExpenseService expenseService;
	private ExpenseDto expenseDto;
	private ExpenseMapper expenseMapper;
	@Test
	public void givenExpenseObject_whenCreateExpense_thenReturnSavedExpense() throws Exception {
		Expense expense = Expense.builder()
				.expenseName("Birthday Party")
				.amount(1500.80)
				.date("2025-08-25").build();
		ExpenseDto expenseDto = expenseMapper.mapToExpenseDto(expense);
		given(expenseService.addExpense(any(ExpenseDto.class)))
		.willAnswer((invocation) -> invocation.getArgument(0));
	    
		// when - action or behaviour that we are going test
		ResultActions response = mockMvc.perform(post("/expense/save")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(expenseDto)));
		
		// then - verify the result or output using assert statements
		response.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.expenseName").value(expense.getExpenseName()))
		.andExpect(jsonPath("$.amount",is(expense.getAmount())))
		.andExpect(jsonPath("$.date",is(expense.getDate())));
		
	
	}
	
	
	
	// Junit Test for Get All expense Rest API
	@Test
	public void givenListOfExpense_whenGetAllExpense_thenReturnExpenseList() throws Exception {
		// given - precondition or setup
		List<ExpenseDto> list = new ArrayList<>();
		 
		Expense expense = Expense.builder()
				.expenseName("Birthday Party")
				.amount(1500.80)
				.date("2025-08-25")
				.build();
		Expense expense1 = Expense.builder()
				.expenseName("Team Fun Activities")
				.amount(14000)
				.date("2025-05-15")
				.build();  
		ExpenseDto expenseDto = expenseMapper.mapToExpenseDto(expense);
		ExpenseDto expenseDto1= expenseMapper.mapToExpenseDto(expense1);
		list.add(expenseDto);
		list.add(expenseDto1);
		given(expenseService.getAllExpense()).willReturn(list);
		// when - action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(get("/expense/list"));
		// then - verify the output
		response.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$.size()",is(list.size())));
		
	}
	// positive scenario - valid expense id
	// Junit test for GET expene by Id REST API
	@Test
	public void givenExpenseById_whenGetExpenseId_thenReturnExpenseObject()throws Exception{
		int expenseId=1;
		Expense expense = Expense.builder()
				.expenseName("Birthday Party")
				.amount(1500.80)
				.date("2025-08-25")
				.build();
		ExpenseDto expenseDto = expenseMapper.mapToExpenseDto(expense);
        given(expenseService.getExpenseById(expenseId)).willReturn((expenseDto));	
        // when - action or the behaviour that we are going test
        ResultActions resultActions = mockMvc.perform(get("/expense/list/{id}"));
        // then - verify the output
        resultActions.andExpect(status().isOk())
        .andExpect(jsonPath("$.expenseName").value(expenseDto.getExpenseName()))
        .andExpect(jsonPath("$.amount", is(expenseDto.getAmount())))
        .andExpect(jsonPath("$.date",is(expenseDto.getDate())));
        
        
	}
	
	
	// negative scenario- valid expense iD
	// Junit test for GET expense by id REST API
	@Test
	public void givenInValidExpenseID_whenGetExpenseById_thenReturnExpenseById()throws Exception{
		// given - precondition or setup
		int expenseId=1;
		Expense expense = Expense.builder()
				.expenseName("Birth Bumps")
				.amount(1500.88)
				.date("2025-08-26")
				.build();
		ExpenseDto expenseDto = expenseMapper.mapToExpenseDto(expense);
        given(expenseService.getExpenseById(expenseId)).willReturn((expenseDto));	
        // when - action or the behaviour that we are going test
        ResultActions resultActions = mockMvc.perform(get("/expense/list/{id}"));
        // then - verify the output
        resultActions.andExpect(status().isNotFound())
        .andDo(print());
	}
	// Junit test for update employee REST API - positive scenario
	@Test
	public void givenUpdateExpense_whenUpdateExpense_thenReturnUpdateExpenseObject() throws Exception {
		// given - precondition or setup
		int expenseId=1;
		Expense savedExpense = Expense.builder()
				.expenseName("Birthday Party")
				.amount(1500.80)
				.date("2025-08-25")
				.build();
		Expense updatedExpense = Expense.builder()
				.expenseName("Birthday Celebration")
				.amount(1800.99)
				.date("2025-08-26")
				.build();
		ExpenseDto savedExpenseDto = expenseMapper.mapToExpenseDto(savedExpense);
		ExpenseDto updatedExpenseDto = expenseMapper.mapToExpenseDto(updatedExpense);
		given(expenseService.getExpenseById(expenseId)).willReturn(savedExpenseDto);
		given(expenseService.updateExpense(expenseId, updatedExpenseDto))
		.willAnswer((invocation) -> invocation.getArgument(0));
		// when - action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(put("/expense/update/{id}", 1)
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(updatedExpenseDto)));
		response.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$.expenseName").value(expenseDto.getExpenseName()))
		.andExpect(jsonPath("$.amount", is(expenseDto.getAmount())))
		.andExpect(jsonPath("$.date",is(expenseDto.getDate())));
	}
	// JUNIT test for update expense REST API - negative scenario
	@Test
	public void givenUpdatedExpense_whenUpdateExpense_thenReturn404()throws Exception{
	// given - precondition or setup
		int expenseId=1;
		Expense savedExpense = Expense.builder()
				.expenseName("Birthday Party")
				.amount(1500.80)
				.date("2025-08-25")
				.build();
		Expense updatedExpense = Expense.builder()
				.expenseName("Birthday Celebration")
				.amount(1800.99)
				.date("2025-08-26")
				.build();
		ExpenseDto savedExpenseDto = expenseMapper.mapToExpenseDto(savedExpense);
		ExpenseDto updatedExpenseDto = expenseMapper.mapToExpenseDto(updatedExpense);
		given(expenseService.getExpenseById(expenseId)).willReturn(savedExpenseDto);
		given(expenseService.updateExpense(expenseId, updatedExpenseDto))
		.willAnswer((invocation) -> invocation.getArgument(0));
		// when - action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(put("/expense/update/{id}", expenseId)
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(updatedExpenseDto)));
		
	}
	@Test
	public void givenExpenseId_whenDeleteExpense_thenReturn200()throws Exception{
		// given - precondition or setup
		int expenseId=1;
		willDoNothing().given(expenseService).deleteExpense(expenseId);
		// when - action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(delete("/expense/delete/{id}"));
		// then - verify the output
		response.andExpect(status().isOk())
		.andDo(print());
	}
}
