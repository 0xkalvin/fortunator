package com.fortunator.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fortunator.api.models.TransactionCategory;
import com.fortunator.api.service.*;

	@WebMvcTest(TransactionCategoryController.class)
public class TransactionCategoryTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionCategoryService service;

    @Test
    public void shouldCreateTransactionCategorySuccessfully() throws Exception {

        TransactionCategory category = new TransactionCategory(null, "Uber", "Ah bate aquela preguiça ne", null);

        MockHttpServletRequestBuilder request = post("/transactions/categories").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(category));

        this.mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnNoContent() throws Exception {
        long userId = 1;

        MockHttpServletRequestBuilder request = get("/transactions/categories/" + userId).contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request).andExpect(status().isNoContent());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
