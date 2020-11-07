package com.fortunator.api.controller.entity;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

public class MonthlyFinancialMovement {

	private BigDecimal total;
	private YearMonth yearMonth;
	private List<MovementByCategory> movementsByCategory;

	public MonthlyFinancialMovement(BigDecimal total, YearMonth yearMonth,
			List<MovementByCategory> movementsByCategory) {
		this.total = total;
		this.yearMonth = yearMonth;
		this.movementsByCategory = movementsByCategory;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public YearMonth getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(YearMonth yearMonth) {
		this.yearMonth = yearMonth;
	}

	public List<MovementByCategory> getMovementsByCategory() {
		return movementsByCategory;
	}

	public void setMovementsByCategory(List<MovementByCategory> movementsByCategory) {
		this.movementsByCategory = movementsByCategory;
	}
}
