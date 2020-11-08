package com.fortunator.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fortunator.api.models.User;
import com.fortunator.api.service.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService service;

	@Test
	public void shouldCreateUserSuccessfully() throws Exception {
		MockHttpServletRequestBuilder request = post("/users").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(new User(null, "kaique", "kaique@boladao.com", "senhadokaique123")));

		this.mockMvc.perform(request).andExpect(status().isCreated());
	}

	@Test
	public void shouldNotCreateDueToInvalidEmail() throws Exception {
		MockHttpServletRequestBuilder request = post("/users").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(new User(null, "kaique", "email", "senhadokaique123")));

		this.mockMvc.perform(request).andExpect(status().isBadRequest());
	}

	@Test
	public void shouldGetUserById() throws Exception {

		MockHttpServletRequestBuilder request = get("/users/1").contentType(MediaType.APPLICATION_JSON);

		this.mockMvc.perform(request).andExpect(status().isOk());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
