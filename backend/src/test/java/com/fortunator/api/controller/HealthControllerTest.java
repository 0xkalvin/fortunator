package com.fortunator.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(HealthController.class)
public class HealthControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldReturnStatusCodeOk() throws Exception {
		this.mockMvc.perform(get("/health"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.status", is("Up and kicking")));
	}
}