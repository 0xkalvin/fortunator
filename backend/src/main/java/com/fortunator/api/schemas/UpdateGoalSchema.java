package com.fortunator.api.schemas;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class UpdateGoalSchema {

	@NotNull
	private Long goalId;
	
    @NotNull
    private BigDecimal progressAmount;

	public Long getGoalId() {
		return goalId;
	}

	public void setGoalId(Long goalId) {
		this.goalId = goalId;
	}

	public BigDecimal getProgressAmount() {
		return progressAmount;
	}

	public void setProgressAmount(BigDecimal progressAmount) {
		this.progressAmount = progressAmount;
	}
}
