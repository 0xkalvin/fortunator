package com.fortunator.api.controller.entity;

import java.util.List;

public class FinancialMovement {

	private List<MonthlyFinancialAmount> expenses;
	private List<MonthlyFinancialAmount> incomings;
	
	public FinancialMovement(List<MonthlyFinancialAmount> expenses, List<MonthlyFinancialAmount> incomings) {
		this.expenses = expenses;
		this.incomings = incomings;
	}

	public List<MonthlyFinancialAmount> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<MonthlyFinancialAmount> expenses) {
		this.expenses = expenses;
	}

	public List<MonthlyFinancialAmount> getIncomings() {
		return incomings;
	}

	public void setIncomings(List<MonthlyFinancialAmount> incomings) {
		this.incomings = incomings;
	}	
}
