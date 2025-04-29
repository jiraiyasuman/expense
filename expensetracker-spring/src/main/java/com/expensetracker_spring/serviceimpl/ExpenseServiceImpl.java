package com.expensetracker_spring.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.expensetracker_spring.dto.ExpenseDto;
import com.expensetracker_spring.entity.Expense;
import com.expensetracker_spring.exception.NotFoundException;
import com.expensetracker_spring.mapper.ExpenseMapper;
import com.expensetracker_spring.repository.ExpenseRepository;
import com.expensetracker_spring.service.ExpenseService;
@Service
public class ExpenseServiceImpl implements ExpenseService{

	private ExpenseRepository expenseRepository;
	ExpenseMapper expenseMapper = new ExpenseMapper();
	@Autowired
	public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
		super();
		this.expenseRepository = expenseRepository;
	}

	@Transactional
	@Override
	public ExpenseDto addExpense(ExpenseDto expenseDto) {
		Expense expense = expenseMapper.mapToExpense(expenseDto);
		Expense savedExpense = expenseRepository.save(expense);
		ExpenseDto savedExpenseDto = expenseMapper.mapToExpenseDto(savedExpense);
		return savedExpenseDto;
	}

	@Override
	public List<ExpenseDto> getAllExpense() {
		List<Expense> list = new ArrayList<>();
		list = expenseRepository.findAll();
		return list.stream().map((l) -> expenseMapper.mapToExpenseDto(l)).collect(Collectors.toList());
	}

	@Override
	public ExpenseDto getExpenseById(int id) {
		Expense expense = expenseRepository.findById(id).get();
		if(expense == null) {
			throw new NotFoundException("Expense not found");
		}
		ExpenseDto expenseDto = expenseMapper.mapToExpenseDto(expense);
		return expenseDto;
	}

	

	@Transactional
	@Override
	public ExpenseDto updateExpense(int id, ExpenseDto expenseDto) {
		Expense expense = expenseRepository.findById(id).get();
		if(expense == null) {
			throw new NotFoundException("Expense not found");
		}
		Expense expenseConverted = expenseMapper.mapToExpense(expenseDto);
		expense = new Expense(expenseConverted.getId(),expenseConverted.getExpenseName(),expenseConverted.getAmount(),expenseConverted.getDate());
		Expense updatedExpense = expenseRepository.save(expense);
		ExpenseDto updatedExpenseDto = expenseMapper.mapToExpenseDto(updatedExpense);
		
		return updatedExpenseDto;
	}

	@Transactional
	@Override
	public void deleteExpense(int id) {
		Expense expense = expenseRepository.findById(id).get();
		if(expense == null) {
			throw new NotFoundException("Expense not found");
		}
		expenseRepository.deleteById(id);
	}

}
