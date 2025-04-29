package com.expensetracker_spring.service;

import java.util.List;

import com.expensetracker_spring.dto.ExpenseDto;

public interface ExpenseService {

	
	public ExpenseDto addExpense(ExpenseDto expenseDto);
	public List<ExpenseDto> getAllExpense();
	public ExpenseDto getExpenseById(int id);
	
	public ExpenseDto updateExpense(int id,ExpenseDto expenseDto);
	public void deleteExpense(int id);
}
