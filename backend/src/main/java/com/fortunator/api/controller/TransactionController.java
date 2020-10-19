package com.fortunator.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fortunator.api.models.Transaction;
import com.fortunator.api.service.TransactionService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@RestController
@RequestMapping(value = "/transactions")
public class TransactionController {

	private static final int SC_BAD_REQUEST = 400;
	private static final int SC_NO_CONTENT = 204;
	private static final int SC_OK = 200;
	private static final int SC_NOT_FOUND = 404;
	private static final int SC_FORBIDDEN = 403;
	
	@Autowired
	private TransactionService transactionService;

	@ApiOperation(value = "Create a new transaction")
	@ApiResponses(value = { @ApiResponse(code = SC_BAD_REQUEST, message = "One or more fields were filled in incorrectly"),
			@ApiResponse(code = SC_FORBIDDEN, message = "When not found user or transaction category"),
			@ApiResponse(code = SC_OK, message = "When success")})
	@CrossOrigin
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Transaction createTransaction(@Valid @RequestBody Transaction transaction) {
		return transactionService.createTransaction(transaction);
	}

	@ApiOperation(value = "Find transaction by year, month and user")
	@ApiResponses(value = { @ApiResponse(code = SC_BAD_REQUEST, message = "One or more fields were filled in incorrectly"),
			@ApiResponse(code = SC_NO_CONTENT, message = "When has no transaction for this month and year"),
			@ApiResponse(code = SC_OK, message = "When has transaction for this month and year"),
			@ApiResponse(code = SC_NOT_FOUND, message = "When not found user")})
	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<Transaction>> getTransactionsByUser(@RequestParam("user_id") Long userId, @RequestParam("year_month") String yearMonth) {
		List<Transaction> transactions = transactionService.findByMonthYearAndUser(yearMonth, userId);			
		if(transactions.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Transaction>>(transactions, HttpStatus.OK);
	}
}