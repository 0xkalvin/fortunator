package com.fortunator.api.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fortunator.api.models.Transaction;

public class BankStatementByMonthDto {
	
	private final BigDecimal totalIncoming;
	private final BigDecimal totalExpense;
	private final List<Transaction> transactions;
	
	public BankStatementByMonthDto(BigDecimal totalIncoming, BigDecimal totalExpense, List<Transaction> transactions) {
		this.totalIncoming = totalIncoming;
		this.totalExpense = totalExpense;
		this.transactions = transactions;
	}

	public BigDecimal getTotalIncoming() {
		return totalIncoming;
	}

	public BigDecimal getTotalExpense() {
		return totalExpense;
	}

	public List<Transaction> getTransactions() {
		return new ArrayList<>(transactions);
	}	
}