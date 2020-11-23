package com.fortunator.api.models;

public enum LevelNameEnum {
    INICIANTE ("Iniciante"),
    INTERMEDIARIO ("Itermediário"),
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