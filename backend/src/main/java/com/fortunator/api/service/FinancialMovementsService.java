package com.fortunator.api.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fortunator.api.controller.entity.FinancialMovement;
import com.fortunator.api.controller.entity.MonthlyFinancialAmount;
import com.fortunator.api.controller.entity.MovementByCategory;
import com.fortunator.api.models.Transaction;
import com.fortunator.api.models.TransactionCategory;

@Service
public class FinancialMovementsService {

	private static final int TOTAL_MONTHS = 12;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private TransactionCategoryService transactionCategoryService;

	public FinancialMovement calculateMonthlyAmountByTransactionType(Integer year, Long userId) {
		List<Transaction> transactions = transactionService.findByYearAndUser(year, userId);

		List<Transaction> expensesTransactions = filterExpensesTransactions(transactions);
		List<Transaction> incomingTrasanctions = filterIncomingTransactions(transactions);

		List<MonthlyFinancialAmount> expenses = calculateTotalByMonthOfTheYear(expensesTransactions, year, userId);
		List<MonthlyFinancialAmount> incomings = calculateTotalByMonthOfTheYear(incomingTrasanctions, year, userId);

		return new FinancialMovement(expenses, incomings);
	}

	public List<MovementByCategory> calculateExpensesByCategory(String yearMonth, Long userId) {
		List<Transaction> transactions = transactionService.findByMonthYearAndUser(yearMonth, userId, Optional.empty());

		List<Transaction> expensesTransactions = filterExpensesTransactions(transactions);

		return calculateAmountPerCategory(expensesTransactions, userId);
	}
	
	List<MonthlyFinancialAmount> calculateTotalByMonthOfTheYear(List<Transaction> transactions, Integer year, Long userId) {
		List<MonthlyFinancialAmount> amountByMonth = new ArrayList<>();
		for (int monthOfYear = 1; monthOfYear <= TOTAL_MONTHS; monthOfYear++) {
			List<Transaction> transactionsByMonth = filterByMonth(transactions, monthOfYear);
			BigDecimal totalByMonth = calculateTotalByMonth(transactionsByMonth);

			MonthlyFinancialAmount financialMovement = new MonthlyFinancialAmount(totalByMonth,
					YearMonth.of(year, monthOfYear));
			amountByMonth.add(financialMovement);
		}
		return amountByMonth;
	}
	
	BigDecimal calculateTotalByMonth(List<Transaction> transactionsByMonth) {
		BigDecimal totalByMonth = BigDecimal.valueOf(0);

		for (Transaction transaction : transactionsByMonth) {
			totalByMonth = totalByMonth.add(transaction.getAmount());
		}
		return totalByMonth;
	}

	List<MovementByCategory> calculateAmountPerCategory(List<Transaction> transactions, Long userId) {
		List<MovementByCategory> movementsByCategory = new ArrayList<>();
		List<TransactionCategory> categories = transactionCategoryService.getCategoriesByUserId(userId);

		for (TransactionCategory category : categories) {
			movementsByCategory.add(new MovementByCategory(category));
		}

		calculateTotalExpenseInMonthByCategory(transactions, movementsByCategory);
		calculateExpensesPercentageInMonthByCategory(movementsByCategory);
		return movementsByCategory;
	}

	void calculateTotalExpenseInMonthByCategory(List<Transaction> transactionsByMonth,
			List<MovementByCategory> movementsByCategory) {

		for (MovementByCategory movementByCategory : movementsByCategory) {
			BigDecimal totalByCategory = BigDecimal.valueOf(0);
			List<Transaction> transactionsByCategory = filterByCategory(transactionsByMonth,
					movementByCategory.getCategory());
			for (Transaction transaction : transactionsByCategory) {
				totalByCategory = totalByCategory.add(transaction.getAmount());
			}
			movementByCategory.setTotal(totalByCategory);
		}
	}

	void calculateExpensesPercentageInMonthByCategory(List<MovementByCategory> movementsByCategory) {
		BigDecimal totalExpensesByMonth = calculateTotalExpenses(movementsByCategory);
		for (MovementByCategory movementByCategory : movementsByCategory) {
			if (!movementByCategory.getTotal().equals(BigDecimal.valueOf(0))
					&& !totalExpensesByMonth.equals(BigDecimal.valueOf(0))) {
				BigDecimal movementsPercentage = movementByCategory.getTotal().multiply(BigDecimal.valueOf(100))
						.divide(totalExpensesByMonth, 2, RoundingMode.HALF_UP);
				movementByCategory.setMovementsPercentage(movementsPercentage);
			} else {
				movementByCategory.setMovementsPercentage(BigDecimal.valueOf(0));
			}
		}
	}

	BigDecimal calculateTotalExpenses(List<MovementByCategory> movementsByCategory) {
		BigDecimal total = BigDecimal.valueOf(0);
		for (MovementByCategory movementByCategory : movementsByCategory) {
			total = total.add(movementByCategory.getTotal());
		}
		return total;
	}

	private List<Transaction> filterByCategory(List<Transaction> transactionsByMonth, TransactionCategory category) {
		return transactionsByMonth.stream().filter(t -> t.getTransactionCategory().equals(category))
				.collect(Collectors.toList());
	}

	private List<Transaction> filterByMonth(List<Transaction> transactions, Integer month) {
		return transactions.stream().filter(t -> Integer.valueOf(t.getDate().getMonth().getValue()).equals(month))
				.collect(Collectors.toList());
	}

	private List<Transaction> filterExpensesTransactions(List<Transaction> transactions) {
		return transactions.stream().filter(t -> t.getType().toString().equals("EXPENSE")).collect(Collectors.toList());
	}

	private List<Transaction> filterIncomingTransactions(List<Transaction> transactions) {
		return transactions.stream().filter(t -> t.getType().toString().equals("INCOMING"))
				.collect(Collectors.toList());
	}
}