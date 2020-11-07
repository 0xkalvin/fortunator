package com.fortunator.api.controller.entity;

import java.util.List;

public class FinancialMovement {

	private List<MonthlyFinancialMovement> expenses;
	private List<MonthlyFinancialMovement> incomings;
	
	public FinancialMovement(List<MonthlyFinancialMovement> expenses, List<MonthlyFinancialMovement> incomings) {
		this.expenses = expenses;
		this.incomings = incomings;
	}

	public List<MonthlyFinancialMovement> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<MonthlyFinancialMovement> expenses) {
		this.expenses = expenses;
	}

	public List<MonthlyFinancialMovement> getIncomings() {
		return incomings;
	}

	public void setIncomings(List<MonthlyFinancialMovement> incomings) {
		this.incomings = incomings;
	}	
}
