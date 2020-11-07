package com.fortunator.api.service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fortunator.api.controller.entity.FinancialMovement;
import com.fortunator.api.controller.entity.MonthlyFinancialMovement;
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

	public FinancialMovement movementsByCategory(Integer year, Long userId) {
		List<Transaction> transactions = transactionService.findByYearAndUser(year, userId);

		List<Transaction> expensesTransactions = filterExpensesTransactions(transactions);
		List<Transaction> incomingTrasanctions = filterIncomingTransactions(transactions);
		
		List<MonthlyFinancialMovement> expenses = generateMonths(expensesTransactions, year, userId);
		List<MonthlyFinancialMovement> incomings = generateMonths(incomingTrasanctions, year, userId);
		
		return new FinancialMovement(expenses, incomings);
	}

	List<MonthlyFinancialMovement> generateMonths(List<Transaction> transactions, Integer year, Long userId) {
		List<MonthlyFinancialMovement> months = new ArrayList<>();
		List<TransactionCategory> categories = transactionCategoryService.getCategoriesByUserId(userId);

		for (int monthOfYear = 1; monthOfYear <= TOTAL_MONTHS; monthOfYear++) {
			List<Transaction> transactionsByMonth = filterByMonth(transactions, monthOfYear);
			List<MovementByCategory> movementsByCategory = new ArrayList<>();
			BigDecimal totalByMonth = calculateTotalByMonth(transactionsByMonth);
			for (TransactionCategory category : categories) {
				movementsByCategory.add(new MovementByCategory(category));
			}
			MonthlyFinancialMovement financialMovement = new MonthlyFinancialMovement(totalByMonth,
					YearMonth.of(year, monthOfYear), movementsByCategory);

			calculateTotalByCategoryAndMonth(transactionsByMonth, financialMovement.getMovementsByCategory());
			months.add(financialMovement);
		}
		calculatePercentageByMonth(months);
		return months;
	}

	void calculateTotalByCategoryAndMonth(List<Transaction> transactionsByMonth,
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
	
	void calculatePercentageByMonth(List<MonthlyFinancialMovement> financialMovements) {
		for(MonthlyFinancialMovement financialMovement : financialMovements) {
			BigDecimal totalByMonth = financialMovement.getTotal();
			for(MovementByCategory movementByCategory : financialMovement.getMovementsByCategory()) {
				if(!movementByCategory.getTotal().equals(BigDecimal.valueOf(0))) {
					BigDecimal movementsPercentage = movementByCategory.getTotal().multiply(BigDecimal.valueOf(100)).divide(totalByMonth);
					movementByCategory.setMovementsPercentage(movementsPercentage);	
				} else {
					movementByCategory.setMovementsPercentage(BigDecimal.valueOf(0));
				}
			}
		}
	}

	BigDecimal calculateTotalByMonth(List<Transaction> transactionsByMonth) {
		BigDecimal totalByMonth = BigDecimal.valueOf(0);

		for (Transaction transaction : transactionsByMonth) {
			totalByMonth = totalByMonth.add(transaction.getAmount());
		}
		return totalByMonth;
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
