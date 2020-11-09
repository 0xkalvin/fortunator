package com.fortunator.api.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fortunator.api.models.Transaction;
import com.fortunator.api.models.TransactionCategory;
import com.fortunator.api.models.TransactionTypeEnum;
import com.fortunator.api.models.User;
import com.fortunator.api.service.TransactionService;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TransactionService transactionService;

	private static final String YEAR_MONTH = "2020-11";
	private static final Long USER_ID = 1L;
	private List<Transaction> transactions = new ArrayList<>();

	@Test
	public void shouldReturnNoContent() throws Exception {
		MockHttpServletRequestBuilder requestResponse = get("/transactions?user_id=1&year_month=2020-11")
				.contentType(MediaType.APPLICATION_JSON);
		this.mockMvc.perform(requestResponse).andExpect(status().isNoContent());
	}

	@Test
	public void shouldReturnTransactionsByMonth() throws Exception {
		transactions.add(createTransaction());
		when(transactionService.findByMonthYearAndUser(YEAR_MONTH, USER_ID)).thenReturn(transactions);

		this.mockMvc.perform(get("/transactions?user_id=1&year_month=2020-11")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].amount", is(2000)))
				.andExpect(jsonPath("$[0].transactionCategory.name", is("salary")))
				.andExpect(jsonPath("$[0].description", is("Salary Incoming")))
				.andExpect(jsonPath("$[0].type", is("INCOMING")));
	}

	private Transaction createTransaction() {
		Transaction transaction = new Transaction();
		transaction.setType(TransactionTypeEnum.INCOMING);
		transaction.setTransactionCategory(createTransactionCategory());
		transaction.setUser(createUser());
		transaction.setAmount(new BigDecimal(2000.0));
		transaction.setDescription("Salary Incoming");
		transaction.setDate(LocalDate.now());
		return transaction;
	}

	private TransactionCategory createTransactionCategory() {
		TransactionCategory transactionCategory = new TransactionCategory();
		transactionCategory.setName("salary");
		transactionCategory.setDescription("salary");
		return transactionCategory;
	}

	private User createUser() {
		User user = new User();
		user.setName("Zé");
		user.setEmail("ze@gmail.com");
		user.setPassword(String.valueOf("senha".hashCode()));
		return user;
	}
}
