package com.fortunator.api.controller.entity;

import java.math.BigDecimal;

public class UpdateUser {

	private Long userId;
	
	private String name;
	
	private String email;
	
	private String oldPassword;
	
	private String newPassword;
	
	private BigDecimal balance;
	
	public UpdateUser() {
	}
	
	public UpdateUser(Long userId, String name, String email, String oldPassword, String newPassword, BigDecimal balance) {
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
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
	
	public String getOldPassword() {
		return oldPassword;
	}
	
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
}
