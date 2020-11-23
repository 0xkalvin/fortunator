package com.fortunator.api.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "goals")
public class Goal {
    private BigDecimal amount;

    @NotNull
    private LocalDate date;

    @NotNull
    @Size(max = 255)
    private String description;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private GoalStatusEnum status;

    @NotNull
    private BigDecimal score;

    @NotNull
    private GoalTypeEnum type;
    
    private BigDecimal progressAmount;
    
    private BigDecimal progressPercentage;

    @JsonProperty(access = Access.WRITE_ONLY)
    @ManyToOne
    private User user;

    public Goal() {
    }

    public Goal(BigDecimal amount, LocalDate date, String description, GoalTypeEnum type) {
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.type = type;
        this.score = calculateScore();
        this.status = GoalStatusEnum.IN_PROGRESS;
        this.progressAmount = BigDecimal.valueOf(0);
        this.progressPercentage = BigDecimal.valueOf(0);
    }

    public BigDecimal calculateScore() {
        BigDecimal ratio = new BigDecimal(10.0);
        BigDecimal score = this.amount.divide(ratio);

        return score;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public GoalStatusEnum getStatus() {
        return status;
    }

    public void setStatus(GoalStatusEnum status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GoalTypeEnum getType() {
        return type;
    }

    public void setType(GoalTypeEnum type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

	public BigDecimal getProgressAmount() {
		return progressAmount;
	}

	public void setProgressAmount(BigDecimal progressAmount) {
		this.progressAmount = progressAmount;
	}

	public BigDecimal getProgressPercentage() {
		return progressPercentage;
	}

	public void setProgressPercentage(BigDecimal progressPercentage) {
		this.progressPercentage = progressPercentage;
	}   
}
