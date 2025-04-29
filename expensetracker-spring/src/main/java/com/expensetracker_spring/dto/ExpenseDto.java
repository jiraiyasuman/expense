package com.expensetracker_spring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ExpenseDto {

	
	private int id;
	@Schema(name = "Expense Name")
	@NotEmpty(message = "Expense Name should not be null")
	@NotNull
	private String expenseName;
	@Schema(name="amount")
	@DecimalMin(value = "0.0",inclusive = true)
	@Positive
	private double amount;
	@Schema(name="date")
	@NotEmpty
	@NotNull
	private String date;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getExpenseName() {
		return expenseName;
	}
	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public ExpenseDto(int id, @NotEmpty(message = "Expense Name should not be null") String expenseName,
			@DecimalMin(value = "0.0", inclusive = true) @Positive double amount, @NotEmpty String date) {
		super();
		this.id = id;
		this.expenseName = expenseName;
		this.amount = amount;
		this.date = date;
	}
	public ExpenseDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ExpenseDto [id=" + id + ", expenseName=" + expenseName + ", amount=" + amount + ", date=" + date + "]";
	}
	
}
