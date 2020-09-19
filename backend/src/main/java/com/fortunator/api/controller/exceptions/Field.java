package com.fortunator.api.controller.exceptions;

public class Field {

	private String fieldName;
	private String message;
	
	public Field() {
	}
	
	public Field(String fieldName, String message) {
		this.fieldName = fieldName;
		this.message = message;
	}
	
	public String getfieldName() {
		return fieldName;
	}
	public void setfieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
