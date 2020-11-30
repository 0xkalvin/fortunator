package com.fortunator.api.controller.entity;

import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailToResetPass {

	@Email
	private String email;

	@JsonCreator
	public EmailToResetPass(@JsonProperty("email") String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
