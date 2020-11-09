package com.fortunator.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fortunator.api.controller.entity.FinancialMovement;
import com.fortunator.api.controller.entity.MovementByCategory;
import com.fortunator.api.service.FinancialMovementsService;

@CrossOrigin
@RestController
@RequestMapping(value = "/financial_movements")
public class FinancialMovementsController {

	@Autowired
	private FinancialMovementsService financialMovementsService;

	@CrossOrigin
	@GetMapping
	public FinancialMovement totalMovementsByMonthOfTheYear(@RequestParam("year") Integer year,
			@RequestParam("user_id") Long userId) {
		return financialMovementsService.calculateMonthlyAmountByTransactionType(year, userId);
	}

	@CrossOrigin
	@GetMapping("/category")
	public List<MovementByCategory> calculateExpensesByCategory(@RequestParam("user_id") Long userId,
			@RequestParam("year_month") String yearMonth) {
		return financialMovementsService.calculateExpensesByCategory(yearMonth, userId);
	}

}
