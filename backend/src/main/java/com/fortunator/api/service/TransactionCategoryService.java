package com.fortunator.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fortunator.api.models.TransactionCategory;
import com.fortunator.api.repository.TransactionCategoryRepository;

@Service
public class TransactionCategoryService {
	
	@Autowired
	private TransactionCategoryRepository transactionCategoryRepository;	
	
	public TransactionCategory createCategory(TransactionCategory transactionCategory) {
		return transactionCategoryRepository.save(transactionCategory);
	}
	
	public List<TransactionCategory> getAllCategories() {
		return transactionCategoryRepository.findAll();
	}
}
