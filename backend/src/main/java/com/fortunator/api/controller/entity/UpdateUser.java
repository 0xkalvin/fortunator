package com.fortunator.api.controller.entity;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

public class UpdateUser {

	@NotBlank
	private Long userId;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private BigDecimal balance;
	
	public UpdateUser() {
	}
	
	public UpdateUser(Long userId, String name, String email, String password, BigDecimal balance) {
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.balance = balance;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
}
