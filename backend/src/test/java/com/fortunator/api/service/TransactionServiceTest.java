package com.fortunator.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import com.fortunator.api.dto.BankStatementByMonthDto;
import com.fortunator.api.models.Transaction;
import com.fortunator.api.models.TransactionCategory;
import com.fortunator.api.models.TransactionTypeEnum;
import com.fortunator.api.models.User;
import com.fortunator.api.repository.TransactionRepository;
import com.fortunator.api.service.exceptions.ResourceNotFoundException;
import com.fortunator.api.service.exceptions.UserNotFoundException;

@ContextConfiguration(classes = { TransactionService.class })
@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

	@Mock
	private TransactionCategoryService transactionCategoryService;
	
	@Mock
	private UserService userSerice;
	
	@Mock
	private BalanceService balanceService;
	
	@Mock
	private TransactionRepository transactionRepository;
	
	@Mock
	private Transaction transaction;
	
	@Mock
	private Transaction expenseTransaction;
	
	@Mock
	private TransactionCategory transactionCategory;
	
	@Mock
	private User mockUser;
	
	@InjectMocks
	private TransactionService transactionService;
	
	private TransactionCategory category = new TransactionCategory(1L, "comida", "comida", mockUser);
	private List<Transaction> transactions = new ArrayList<>();
	private User user  = new User(1L, "Zé", "ze@gmail.com", "senha");
	private Optional<TransactionCategory> emptyTransactionCategory = Optional.empty();
	private Optional<TransactionCategory> optionalTransactionCategory = Optional.of(category);
	private Optional<User> optionalUser = Optional.of(user);
	private Optional<User> emptyUser = Optional.empty();
	
	@Test
	public void shouldThrowResourceNotFoundException() {
		when(transactionCategoryService.findById(anyLong())).thenReturn(emptyTransactionCategory);
		when(transaction.getTransactionCategory()).thenReturn(transactionCategory);
		when(transactionCategory.getId()).thenReturn(1L);
		
		ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class,
				() -> transactionService.createTransaction(transaction) );
		assertTrue(thrown.getMessage().equals("Category not found"));
	}
	
	@Test
	public void shouldThrowUserNotFoundException() {
		when(transactionCategoryService.findById(anyLong())).thenReturn(optionalTransactionCategory);
		when(transaction.getTransactionCategory()).thenReturn(category);
		when(transaction.getUser()).thenReturn(mockUser);
		when(mockUser.getId()).thenReturn(2L);
		when(userSerice.findUserById(anyLong())).thenReturn(emptyUser);
		
		UserNotFoundException thrown = assertThrows(UserNotFoundException.class,
				() -> transactionService.createTransaction(transaction) );
		assertTrue(thrown.getMessage().equals("User not found when create transaction"));
	}
	
	@Test
	public void shouldSaveNewTransaction() {
		when(transactionCategoryService.findById(anyLong())).thenReturn(optionalTransactionCategory);
		when(transaction.getTransactionCategory()).thenReturn(category);
		when(userSerice.findUserById(anyLong())).thenReturn(optionalUser);
		when(transaction.getUser()).thenReturn(user);
		when(transaction.getAmount()).thenReturn(BigDecimal.valueOf(2.0));
		when(transaction.getType()).thenReturn(TransactionTypeEnum.INCOMING);
		doNothing().when(balanceService).updateBalance(1L, BigDecimal.valueOf(2.0), "INCOMING");
		
		transactionService.createTransaction(transaction);
		
		verify(transactionRepository).save(transaction);		
	}
	
	@Test
	public void shouldThrowUserNotFoundExceptionWhenFetchTransaction() {
		when(userSerice.findUserById(1L)).thenReturn(emptyUser);
		
		UserNotFoundException thrown = assertThrows(UserNotFoundException.class,
				() -> transactionService.findByMonthYearAndUser("", 1L));
		assertTrue(thrown.getMessage().equals("User not found"));
	}
	
	@Test
	public void shouldReturnExtractByMonth() {
		transactions.add(transaction);
		transactions.add(expenseTransaction);
		when(userSerice.findUserById(1L)).thenReturn(optionalUser);
		doReturn(transactions).when(transactionRepository).findByMonthYearAndUser(2020, 10, 1L);
		
		List<Transaction> transactionsResult = transactionService.findByMonthYearAndUser("2020-10", 1L);
		
		assertEquals(transactions.size(), transactionsResult.size());
	}
}
