package com.fortunator.api.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fortunator.api.models.Transaction;
import com.fortunator.api.models.TransactionCategory;
import com.fortunator.api.models.TransactionTypeEnum;
import com.fortunator.api.models.User;
import com.fortunator.api.repository.TransactionRepository;
import com.fortunator.api.service.exceptions.ResourceNotFoundException;
import com.fortunator.api.service.exceptions.UserNotFoundException;

@Service
public class TransactionService {

	@Autowired
	private TransactionCategoryService transactionCategoryService;

	@Autowired
	private UserService userService;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private BalanceService balanceService;

	@Transactional
	public Transaction createTransaction(Transaction transaction) {
		TransactionCategory transactionCategory = transactionCategoryService
				.findById(transaction.getTransactionCategory().getId())
				.orElseThrow(() -> new ResourceNotFoundException("Category not found"));
		transaction.setTransactionCategory(transactionCategory);

		User user = userService.findUserById(transaction.getUser().getId())
				.orElseThrow(() -> new UserNotFoundException("User not found when create transaction"));
		transaction.setUser(user);

		balanceService.updateBalance(transaction.getUser().getId(), transaction.getAmount(),
				transaction.getType().toString());
		return transactionRepository.save(transaction);
	}

	public List<Transaction> findByMonthYearAndUser(String yearAndMonth, Long userId) {
		if (!userService.findUserById(userId).isPresent()) {
			throw new UserNotFoundException("User not found");
		}
		String[] yearAndMonthArray = yearAndMonth.split("-");
		return transactionRepository.findByMonthYearAndUser(Integer.valueOf(yearAndMonthArray[0]),
				Integer.valueOf(yearAndMonthArray[1]), userId);
	}
	
	Map<String, BigDecimal> calculateTotalValuesByMonth(List<Transaction> transactions) {
		BigDecimal totalIncoming = new BigDecimal(0);
		BigDecimal totalExpense = new BigDecimal(0);
		
		for(Transaction transaction : transactions) {
			if(transaction.getType().equals(TransactionTypeEnum.INCOMING)) {
				totalIncoming = totalIncoming.add(transaction.getAmount());
			} else {
				totalExpense = totalExpense.add(transaction.getAmount());
			}
		}
		Map<String, BigDecimal> totalValues = new HashMap<>();
		totalValues.put("incoming", totalIncoming);
		totalValues.put("expense", totalExpense);
		return totalValues;
	}
}
