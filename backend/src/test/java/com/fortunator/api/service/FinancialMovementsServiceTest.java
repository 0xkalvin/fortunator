package com.fortunator.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import com.fortunator.api.controller.entity.FinancialMovement;
import com.fortunator.api.controller.entity.MonthlyFinancialAmount;
import com.fortunator.api.controller.entity.MovementByCategory;
import com.fortunator.api.models.Transaction;
import com.fortunator.api.models.TransactionCategory;
import com.fortunator.api.models.TransactionTypeEnum;

@ContextConfiguration(classes = { FinancialMovementsService.class })
@ExtendWith(MockitoExtension.class)
public class FinancialMovementsServiceTest {

	private static final int FEBRUARY = 2;
	private static final Integer YEAR = 2020;
	private static final Integer DAY = 1;
	private static final Long USER_ID = 1L;
	private static final Integer TOTAL_MONTHS = 12;
	private static final String YEAR_MONTH = "2020-2";

	@Mock
	private TransactionService transactionService;
	
	@Mock
	private TransactionCategoryService transactionCategoryService;

	@Mock
	private Transaction expenseTransaction;

	@Mock
	private Transaction incomingTransaction;
	
	@Mock
	private TransactionCategory foodTransactionCategory;
	
	@Mock
	private TransactionCategory busTransactionCategory;
	

	@InjectMocks
	private FinancialMovementsService financialMovementsService;

	private List<Transaction> transactions = new ArrayList<>();
	private List<TransactionCategory> categories = new ArrayList<>();
	private LocalDate date = LocalDate.of(YEAR, Month.FEBRUARY, DAY);

	@Test
	public void shouldReturnMonthlyAmount() {
		transactions.add(expenseTransaction);
		transactions.add(incomingTransaction);

		when(transactionService.findByYearAndUser(YEAR, USER_ID)).thenReturn(transactions);
		when(expenseTransaction.getType()).thenReturn(TransactionTypeEnum.EXPENSE);
		when(incomingTransaction.getType()).thenReturn(TransactionTypeEnum.INCOMING);
		when(expenseTransaction.getDate()).thenReturn(date);
		when(incomingTransaction.getDate()).thenReturn(date);
		when(expenseTransaction.getAmount()).thenReturn(BigDecimal.valueOf(50.0));
		when(incomingTransaction.getAmount()).thenReturn(BigDecimal.valueOf(100.0));

		FinancialMovement result = financialMovementsService.calculateMonthlyAmountByTransactionType(YEAR, USER_ID);

		assertTrue(BigDecimal.valueOf(50.0).compareTo(getMonth(result.getExpenses(), FEBRUARY).getTotal()) == 0);
		assertTrue(BigDecimal.valueOf(100.0).compareTo(getMonth(result.getIncomings(), FEBRUARY).getTotal()) == 0);
		assertEquals(result.getExpenses().size(), TOTAL_MONTHS);
		assertEquals(result.getIncomings().size(), TOTAL_MONTHS);
	}
	
	@Test
	public void shouldReturnExpensesByCategory() {
		transactions.add(expenseTransaction);
		transactions.add(incomingTransaction);
		categories.add(busTransactionCategory);
		categories.add(foodTransactionCategory);
		
		when(transactionService.findByMonthYearAndUser(YEAR_MONTH, USER_ID)).thenReturn(transactions);
		when(expenseTransaction.getType()).thenReturn(TransactionTypeEnum.EXPENSE);
		when(incomingTransaction.getType()).thenReturn(TransactionTypeEnum.INCOMING);
		when(transactionCategoryService.getCategoriesByUserId(USER_ID)).thenReturn(categories);
		
		when(expenseTransaction.getTransactionCategory()).thenReturn(foodTransactionCategory);
		when(expenseTransaction.getAmount()).thenReturn(BigDecimal.valueOf(50.0));
		
		
		List<MovementByCategory> result = financialMovementsService.calculateExpensesByCategory(YEAR_MONTH, USER_ID);
		
		assertEquals(result.size(), 2);
		assertTrue(BigDecimal.valueOf(50.0).compareTo(totalExpenses(result)) == 0);
		assertTrue(BigDecimal.valueOf(50.0).compareTo(getMovementByCategoryByCategory(result, foodTransactionCategory).getTotal()) == 0);
		assertTrue(BigDecimal.valueOf(0).compareTo(getMovementByCategoryByCategory(result, busTransactionCategory).getTotal()) == 0);
	}
	
	private MovementByCategory getMovementByCategoryByCategory(List<MovementByCategory> result, TransactionCategory transactionCategory) {
		for(MovementByCategory movementByCategory : result) {
			if(movementByCategory.getCategory().equals(transactionCategory)) {
				return movementByCategory;
			}
		}
		return new MovementByCategory();
	}
	
	private MonthlyFinancialAmount getMonth(List<MonthlyFinancialAmount> amountsByMonth,
			int month) {
		for(MonthlyFinancialAmount monthAmount : amountsByMonth) {
			if(monthAmount.getYearMonth().getMonth().getValue() == month) {
				return monthAmount;
			}
		}
		return new MonthlyFinancialAmount();
	}
	
	private BigDecimal totalExpenses(List<MovementByCategory> methodResult){
		BigDecimal total = BigDecimal.valueOf(0);
		for(MovementByCategory result : methodResult) {
			total = total.add(result.getTotal());
		}
		return total;
	}

}
