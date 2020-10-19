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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "transactions")
public class Transaction {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private TransactionCategory transactionCategory;
	
	@ManyToOne
	private User user;
	
	@NotNull
	private BigDecimal amount;
	
	private String description;
	
	@NotNull
	private TransactionTypeEnum type;
	
	private LocalDate date;
	
	public Transaction() {
	}

	public Transaction(Long id, TransactionCategory transactionCategory, User user, BigDecimal amount, String description,
			TransactionTypeEnum type, LocalDate date) {
		this.id = id;
		this.transactionCategory = transactionCategory;
		this.user = user;
		this.amount = amount;
		this.description = description;
		this.type = type;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TransactionCategory getTransactionCategory() {
		return transactionCategory;
	}

	public void setTransactionCategory(TransactionCategory transactionCategory) {
		this.transactionCategory = transactionCategory;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TransactionTypeEnum getType() {
		return type;
	}

	public void setType(TransactionTypeEnum type) {
		this.type = type;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}