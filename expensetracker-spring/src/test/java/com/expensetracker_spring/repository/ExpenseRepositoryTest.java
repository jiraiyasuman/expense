package com.expensetracker_spring.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import com.expensetracker_spring.dto.ExpenseDto;
import com.expensetracker_spring.entity.Expense;
import com.expensetracker_spring.mapper.ExpenseMapper;

@DataJpaTest
public class ExpenseRepositoryTest {

	private ExpenseRepository expenseRepository;
	
	@Autowired
	public ExpenseRepositoryTest(ExpenseRepository expenseRepository) {
		super();
		this.expenseRepository = expenseRepository;
	}

	private ExpenseDto expenseDto;
	
	private ExpenseMapper expenseMapper;

	private Expense expense;
	@BeforeEach
	public void setUp() {
		expense = Expense.builder()
				.expenseName("Lunch Time")
				.amount(150.25)
				.date("2025-08-15")
				.build();
	}
	// Junit test for save expense operation
	@DisplayName("Junt test for save expense operation")
	@Test
	public void givenExpenseObject_whenSave_thenReturnSaveExpense() {
		// given - precondition or setup
		Expense expense = Expense.builder()
				.expenseName("Lunch Time")
				.amount(1560.25)
				.date("2025-08-15")
				.build();
		// when - action or the behaviour that we are going test
		Expense savedExpense = expenseRepository.save(expense);
		// then - verify the output
		assertThat(savedExpense).isNotNull();
	}
	
	@DisplayName("Junit test for get all expense operation")
	@Test
	public void givenExpenseList_whenFindAll_thenExpenseList() {
		Expense expense = Expense.builder()
				.expenseName("Lunch Time")
				.amount(1560.25)
				.date("2025-08-15")
				.build();
		Expense expense1 = Expense.builder()
				.expenseName("Dinner Time")
				.amount(1706.56)
				.date("2025-08-16")
				.build();
		expenseRepository.save(expense);
		expenseRepository.save(expense1);
		// when - action or the behaviour that we are going test
		List<Expense> expenseList = expenseRepository.findAll();
		// then - verify the output
		assertThat(expenseList).isNotNull();
		assertThat(expenseList.size()).isEqualTo(2);
		// 
	}
	
	// Junit test for expense by id operation
	@DisplayName("JUnit test for get expense by id operation")
	@Test
	public void givenExpenseObject_whenFindById_thenReturnExpenseObject() {
	// given - precondition or setup
		Expense expense = Expense.builder()
				.expenseName("Lunch Time")
				.amount(1560.25)
				.date("2025-08-15")
				.build();
		expenseRepository.save(expense);
		// when - action or the behaviour that we are going test
		Expense expenseDb = expenseRepository.findById(expense.getId()).get();
		// then - verify the output
		assertThat(expenseDb).isNotNull();
	}
	// Junit test for get expense by email operation
	@DisplayName("Junit test for update expense operation")
	@Test
	public void givenExpenseObject_whenUpdateExpense_thenReturnUpdatedExpense() {
		Expense expense = Expense.builder()
				.expenseName("Lunch Time")
				.amount(1560.25)
				.date("2025-08-15")
				.build();
		expenseRepository.save(expense);
		// when - action or the behaviour that we are going test
		Expense savedExpense = expenseRepository.findById(expense.getId()).get();
		savedExpense.setExpenseName("Office Itinery");
		savedExpense.setAmount(15000);
		savedExpense.setDate("2025-08-16");
		Expense updatedExpense = expenseRepository.save(savedExpense);
		// then - verify the output
		assertThat(updatedExpense.getExpenseName()).isEqualTo("Office Itinery");
		assertThat(updatedExpense.getAmount()==15000);
		
	}
	@DisplayName("Junit test for delete expense")
	@Test
	public void givenExpenseObject_whenDelete_thenRemoveExpense() {
		// given - precondition or setup
		Expense expense = Expense.builder()
				.expenseName("Lunch Time")
				.amount(1560.25)
				.date("2025-08-15")
				.build();
		expenseRepository.save(expense);
		// when - action or the behaviour that we are going test
		expenseRepository.deleteById(expense.getId());
		Optional<Expense> expenOptional = expenseRepository.findById(expense.getId());
		// then verify the output
		assertThat(expenOptional).isEmpty();
	}
}
