package com.fortunator.api.controller.entity;

import java.math.BigDecimal;

import com.fortunator.api.models.TransactionCategory;

public class MovementByCategory {

	private TransactionCategory category;
	private BigDecimal total;
	private BigDecimal movementsPercentage;
	
	public MovementByCategory(TransactionCategory category) {
		this.category = category;
	}

	public TransactionCategory getCategory() {
		return category;
	}
	public void setCategory(TransactionCategory category) {
		this.category = category;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public BigDecimal getMovementsPercentage() {
		return movementsPercentage;
	}
	public void setMovementsPercentage(BigDecimal movementsPercentage) {
		this.movementsPercentage = movementsPercentage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MovementByCategory other = (MovementByCategory) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		return true;
	}
}
