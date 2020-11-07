package com.fortunator.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fortunator.api.controller.entity.FinancialMovement;
import com.fortunator.api.service.FinancialMovementsService;

@RestController
@RequestMapping(value = "/financial-movements")
public class FinancialMovementsController {
	
	@Autowired
	private FinancialMovementsService financialMovementsService;

	@GetMapping("/{year}/{userId}")
	public FinancialMovement totalAmountByMonthOfTheYear(@PathVariable Integer year, @PathVariable Long userId) {
		return financialMovementsService.movementsByCategory(year, userId);
	}
	
}
