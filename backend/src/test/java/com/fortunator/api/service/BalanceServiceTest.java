package com.fortunator.api.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import com.fortunator.api.models.Balance;
import com.fortunator.api.models.TransactionTypeEnum;
import com.fortunator.api.models.User;
import com.fortunator.api.repository.BalanceRepository;
import com.fortunator.api.service.exceptions.ResourceNotFoundException;

@ContextConfiguration(classes = { BalanceService.class })
@ExtendWith(MockitoExtension.class)
public class BalanceServiceTest {

	private static final Long USER_ID = 1L;

	@Mock
	private BalanceRepository balanceRepository;
	
	private Balance balance = new Balance(new User(), new BigDecimal(10.0));
	private Optional<Balance> emptyBalance = Optional.empty();
	private Optional<Balance> optionalBalance = Optional.of(balance);
	private BigDecimal amount = new BigDecimal(2.0);
	
	@InjectMocks
	private BalanceService balanceService;

	@Test
	public void shouldThrowResourceNotFoundException() {
		when(balanceRepository.findByUserId(anyLong())).thenReturn(emptyBalance);

		ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class,
				() -> balanceService.updateBalance(USER_ID, amount, TransactionTypeEnum.INCOMING.toString()));
		assertTrue(thrown.getMessage().contains("Balance not found"));
	}

	@Test
	public void shouldAddAmount() {
		when(balanceRepository.findByUserId(anyLong())).thenReturn(optionalBalance);
		
		balanceService.updateBalance(USER_ID, amount, "INCOMING");
		
		assertTrue(new BigDecimal(12.0).compareTo(balance.getAmount()) == 0);
		verify(balanceRepository).save(balance);
	}
	
	@Test
	public void shouldSubtractAmount() {
		when(balanceRepository.findByUserId(anyLong())).thenReturn(optionalBalance);
		
		balanceService.updateBalance(USER_ID, amount, "EXPENSE");
		
		assertTrue(new BigDecimal(8.0).compareTo(balance.getAmount()) == 0);
		verify(balanceRepository).save(balance);
	}
}
