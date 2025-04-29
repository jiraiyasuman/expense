package com.expensetracker_spring.mapper;

import com.expensetracker_spring.dto.ExpenseDto;
import com.expensetracker_spring.entity.Expense;

public class ExpenseMapper {

	
	public ExpenseDto mapToExpenseDto(Expense expense) {
		ExpenseDto expenseDto = new ExpenseDto(
				expense.getId(),
				expense.getExpenseName(),
				expense.getAmount(),
				expense.getDate()
				);
		return expenseDto;
	}
	
	public Expense mapToExpense(ExpenseDto expenseDto) {
		Expense expense = new Expense(
				expenseDto.getId(),
				expenseDto.getExpenseName(),
				expenseDto.getAmount(),
				expenseDto.getDate()
				);
		return expense;
	}
}
