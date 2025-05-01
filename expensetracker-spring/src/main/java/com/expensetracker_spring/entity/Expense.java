package com.expensetracker_spring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
@Entity(name="expense")
@Builder
public class Expense {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="expense_name")
	private String expenseName;
	@Column(name="amount")
	private double amount;
	@Column(name="date")
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
	public Expense(int id, String expenseName, double amount, String date) {
		super();
		this.id = id;
		this.expenseName = expenseName;
		this.amount = amount;
		this.date = date;
	}
	public Expense() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Expense [id=" + id + ", expenseName=" + expenseName + ", amount=" + amount + ", date=" + date + "]";
	}
	
}
