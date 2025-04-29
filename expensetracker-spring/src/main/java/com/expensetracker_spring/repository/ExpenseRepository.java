package com.expensetracker_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expensetracker_spring.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Integer>{

	
}
