package com.fortunator.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fortunator.api.models.TransactionCategory;
import com.fortunator.api.service.TransactionCategoryService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@RestController
@RequestMapping(value = "/transactions/categories")
public class TransactionCategoryController {

	private static final int SC_OK = 200;
	private static final int SC_BAD_REQUEST = 400;
	private static final int NO_CONTENT = 204;
	
	@Autowired
	private TransactionCategoryService transactionCategoryService;

	@ApiOperation(value = "Create a new transaction category ")
	@ApiResponses(value = { @ApiResponse(code = SC_BAD_REQUEST, message = "One or more fields were filled in incorrectly") })
	@CrossOrigin
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TransactionCategory createCategory(@Valid @RequestBody TransactionCategory transactionCategory) {
		return transactionCategoryService.createCategory(transactionCategory);
	}

	@CrossOrigin
	@ApiOperation(value = "Get all categories")
	@ApiResponses(value = { @ApiResponse(code = SC_OK, message = "ok"),
			@ApiResponse(code = NO_CONTENT, message = "When has no categories.") })
	@GetMapping("/{userId}")
	public ResponseEntity<List<TransactionCategory>> getCategoriesByUser(@PathVariable Long userId) {
		List<TransactionCategory> categories = transactionCategoryService.getCategoriesByUserId(userId);
		if(categories.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<TransactionCategory>>(categories, HttpStatus.OK);
	}
}