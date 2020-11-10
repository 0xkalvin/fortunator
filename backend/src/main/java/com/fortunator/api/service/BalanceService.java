package com.fortunator.api.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fortunator.api.models.Balance;
import com.fortunator.api.models.TransactionTypeEnum;
import com.fortunator.api.repository.BalanceRepository;
import com.fortunator.api.service.exceptions.ResourceNotFoundException;

@Service
public class BalanceService {

	@Autowired
	private BalanceRepository balanceRepository;

	public void updateBalance(Long userId, BigDecimal amount, String type) {
		Balance balance = balanceRepository.findByUserId(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Balance not found"));

		if (type.equals(TransactionTypeEnum.INCOMING.toString())) {
			balance.setAmount(balance.getAmount().add(amount));
		} else {
			balance.setAmount(balance.getAmount().subtract(amount));
		}
		balanceRepository.save(balance);
	}
}