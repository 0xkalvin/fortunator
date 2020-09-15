package com.fortunator.api.controller;

import java.util.Map;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/health")
public class HealthController {
	
	@GetMapping
	public ResponseEntity<Map<String, String>> healthCheck(){
		Map<String, String> response = new HashMap<>();
		response.put("status", "Up and kicking");

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
}
