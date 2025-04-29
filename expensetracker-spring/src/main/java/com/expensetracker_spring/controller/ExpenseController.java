package com.expensetracker_spring.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensetracker_spring.dto.ExpenseDto;
import com.expensetracker_spring.service.ExpenseService;

import jakarta.validation.Valid;

@CrossOrigin("localhost:3000")
@RestController
@RequestMapping("expense")
public class ExpenseController {

	private ExpenseService expenseService;

	@Autowired
	public ExpenseController(ExpenseService expenseService) {
		super();
		this.expenseService = expenseService;
	}
	@PostMapping("save")
	public ResponseEntity<ExpenseDto> saveExpense(@Valid @RequestBody  ExpenseDto expenseDto){
		ExpenseDto savedExpenseDto = expenseService.addExpense(expenseDto);
		return ResponseEntity.ok(savedExpenseDto);
	}
	@GetMapping("list")
	public ResponseEntity<List<ExpenseDto>> getAll(){
		List<ExpenseDto> list = expenseService.getAllExpense();
		return ResponseEntity.ok(list);
	}
	@GetMapping("list/{id}")
	public ResponseEntity<ExpenseDto> getById(@PathVariable("id") int id){
		ExpenseDto expenseDto = expenseService.getExpenseById(id);
		return ResponseEntity.ok(expenseDto);
	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<ExpenseDto> updateExpense(@PathVariable("id") int id, @Valid @RequestBody  ExpenseDto expenseDto){
		ExpenseDto updatedDto = expenseService.updateExpense(id, expenseDto);
		return ResponseEntity.ok(updatedDto);
		
	}
	@DeleteMapping("delete/{id}")
	public ResponseEntity<String> deleteExpense(@PathVariable("id") int id){
		expenseService.deleteExpense(id);
		return ResponseEntity.ok("Expense Deleted successfully");
	}
}
