package com.fortunator.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fortunator.api.models.TransactionCategory;
import com.fortunator.api.models.User;
import com.fortunator.api.repository.TransactionCategoryRepository;
import com.fortunator.api.repository.UserRepository;
import com.fortunator.api.service.exceptions.UserNotFoundException;

@Service
public class TransactionCategoryService {
	
	@Autowired
	private TransactionCategoryRepository transactionCategoryRepository;	
	
	@Autowired
	private UserRepository userRepository;
	
	public TransactionCategory createCategory(TransactionCategory transactionCategory) {
		User user = userRepository.findById(transactionCategory.getUser().getId())
				.orElseThrow(() -> new UserNotFoundException("User not exists"));
		
		transactionCategory.setUser(user);
		transactionCategory.setName(transactionCategory.getName().replaceAll(" ", "_").toLowerCase());
		return transactionCategoryRepository.save(transactionCategory);
	}
	
	public List<TransactionCategory> getCategoriesByUserId(Long userId) {
		return transactionCategoryRepository.findByUserId(userId);
	}
}