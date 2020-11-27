package com.fortunator.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fortunator.api.models.TransactionCategory;
import com.fortunator.api.models.User;
import com.fortunator.api.repository.TransactionCategoryRepository;
import com.fortunator.api.repository.UserRepository;
import com.fortunator.api.service.exceptions.ResourceNotFoundException;
import com.fortunator.api.service.exceptions.UserNotFoundException;

@Service
public class TransactionCategoryService {
	
	private static final boolean DISABLE = false;

	@Autowired
	private TransactionCategoryRepository transactionCategoryRepository;

	@Autowired
	private UserRepository userRepository;

	public TransactionCategory createCategory(TransactionCategory transactionCategory) {
		User user = userRepository.findById(transactionCategory.getUser().getId())
				.orElseThrow(() -> new UserNotFoundException("User does not exist"));

		transactionCategory.setUser(user);
		transactionCategory.setName(transactionCategory.getName().replaceAll(" ", "_").toLowerCase());
		return transactionCategoryRepository.save(transactionCategory);
	}

	public List<TransactionCategory> getCategoriesByUserId(Long userId) {
		List<TransactionCategory> defaultCategories = transactionCategoryRepository.findByIsDefault(true);
		List<TransactionCategory> userCategories = filterActiveCategories(transactionCategoryRepository.findByUserId(userId));

		defaultCategories.addAll(userCategories);

		return defaultCategories;

	}
	
	public List<TransactionCategory> getCreatedCategories(Long userId){
		return filterActiveCategories(transactionCategoryRepository.findByUserId(userId));
	}

	private List<TransactionCategory> filterActiveCategories(List<TransactionCategory> categories) {
		return categories.stream().filter(category -> category.isActive()).collect(Collectors.toList());
	}

	public Optional<TransactionCategory> findById(Long transactionCategoryId) {
		return transactionCategoryRepository.findById(transactionCategoryId);
	}

	public void disableCategory(Long categoryId) {
		TransactionCategory category = transactionCategoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found"));
		
		category.setActive(DISABLE);
		
		transactionCategoryRepository.save(category);
	}
}
