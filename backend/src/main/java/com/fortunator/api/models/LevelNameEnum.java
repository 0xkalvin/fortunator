package com.fortunator.api.models;

public enum LevelNameEnum {
    INICIANTE ("Iniciante"),
    INTERMEDIARIO ("Intermediário"),
    AVANCADO ("Avançado"),
    SUPER_AVANCADO ("Super Avançado");

	private String description;
	
	LevelNameEnum(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}