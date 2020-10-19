package com.fortunator.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fortunator.api.models.TransactionCategory;

@Repository
public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, Long>{

	List<TransactionCategory> findByUserId(Long userId);
	List<TransactionCategory> findByIsDefault(Boolean isDefault);
}
