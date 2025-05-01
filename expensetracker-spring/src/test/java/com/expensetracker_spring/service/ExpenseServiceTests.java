package com.expensetracker_spring.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import com.expensetracker_spring.dto.ExpenseDto;
import com.expensetracker_spring.entity.Expense;
import com.expensetracker_spring.mapper.ExpenseMapper;
import com.expensetracker_spring.repository.ExpenseRepository;
import com.expensetracker_spring.serviceimpl.ExpenseServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTests {

	@Mock
	private ExpenseRepository expenseRepository;
	@InjectMocks
	private ExpenseServiceImpl expenseServiceImpl;
	
	private Expense expense;
	
	private ExpenseMapper expenseMapper;
	private ExpenseDto expenseDto;
	
	@BeforeEach
	public void setUp() {
		expense = Expense.builder()
				.id(1)
				.expenseName("Rapido Cab Fare")
				.amount(300.56)
				.date("2025-04-30 ")
				.build();
	}
	
	// Junit test for add expenses
	@DisplayName("Junit test for saveExpense method")
	@Test
	public void givenExpenseObject_whenSaveExpense_thenReturnExpenseObject() {
		    given(expenseRepository.findById(expense.getId()))
		    .willReturn(Optional.empty());
		    given(expenseRepository.save(expense)).willReturn(expense);
		    System.out.println(expenseRepository);
		    System.out.println(expenseServiceImpl);
		    ExpenseDto expenseDto = expenseMapper.mapToExpenseDto(expense);
		    ExpenseDto savedExpenseDto= expenseServiceImpl.addExpense(expenseDto);
		    System.out.println(expense.toString());
		    assertThat(savedExpenseDto).isNotNull();
	}
	
	@DisplayName("Junit test for getAllExenses method")
	@Test
	public void givenExpenseList_whenGetAllExpenses_thenReturnExpenseList() {
	
		Expense expense1 = Expense.builder()
				.id(2)
				.expenseName("Lunch")
				.amount(100)
				.date("2025-04-08")
				.build();
		given(expenseRepository.findAll()).willReturn(List.of(expense,expense1));
		// when - action or the behaviour that we are going test
		List<ExpenseDto> expenseList = expenseServiceImpl.getAllExpense();
		//then - verify the output
		assertThat(expenseList).isNotNull();
		assertThat(expenseList.size()).isEqualTo(2);
		
	}
	@DisplayName("Junit test for getAllExpense method (negative scenario)")
	@Test
	public void givenExpenseList_whenGetAllExpense_thenReturnEmptyExpenseList() {
		// given - precondition or setup
		Expense expense1 = Expense.builder()
				.id(2)
				.expenseName("Dinner")
				.amount(856.89)
				.date("2025-08-25")
				.build();
		given(expenseRepository.findAll()).willReturn(Collections.emptyList());
		// when - action or the behaviour that we are going test
		List<ExpenseDto> expenseList = expenseServiceImpl.getAllExpense();
		// then - verify the output
		assertThat(expenseList).isEmpty();
		assertThat(expenseList.size()).isEqualTo(0);
	}
	@DisplayName("Junit test for getExpenseById method")
	@Test
	public void givenExpenseById_whenGetExpenseyId_thenReturnExpenseObject() {
		// given 
		given(expenseRepository.findById(1)).willReturn(Optional.of(expense));
		// when
		ExpenseDto expenseDto = expenseServiceImpl.getExpenseById(expense.getId());
		// thenexpens
		assertThat(expenseDto).isNotNull();
		
	}
	
	@DisplayName("JUNIT test for update expense method")
	@Test
	public void givenExpenseObject_whenUpdateExpense_thenReturnUpdatedExpense() {
		// given - precondition or setup
		given(expenseRepository.save(expense)).willReturn(expense);
		expense.setExpenseName("Client Lunch");
		expense.setAmount(1560.89);
		expense.setDate("2025-04-01");
		ExpenseDto expenseDto = expenseMapper.mapToExpenseDto(expense);
		ExpenseDto savedExpenseDto = expenseServiceImpl.updateExpense(expenseDto.getId(), expenseDto);
		assertThat(savedExpenseDto.getExpenseName().equalsIgnoreCase("CLIENT LUNCH"));
		assertThat(savedExpenseDto.getAmount()==1560.89);
		
	}
	
	//JUnit test for delete Employee method
	@DisplayName("Junit test for deleteEmployee method")
	@Test
	public void givenExpenseById_whenDeleteExpense_thenNothing() {
		// given - precondition or setup
		int expenseId = 1;
		willDoNothing().given(expenseRepository).deleteById(expenseId);
		// when - action or the behaviour that we are going test
		expenseServiceImpl.deleteExpense(expenseId);
		
		// then - verify the output
		verify(expenseRepository,times(1)).deleteById(expenseId);
	}
}
