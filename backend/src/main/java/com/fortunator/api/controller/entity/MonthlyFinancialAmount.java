package com.fortunator.api.controller.entity;

import java.math.BigDecimal;
import java.time.YearMonth;

public class MonthlyFinancialAmount {

	private BigDecimal total;
	private YearMonth yearMonth;

	public MonthlyFinancialAmount(BigDecimal total, YearMonth yearMonth) {
		this.total = total;
		this.yearMonth = yearMonth;
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
}
