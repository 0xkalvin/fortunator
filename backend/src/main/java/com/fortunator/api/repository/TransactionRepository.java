package com.fortunator.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fortunator.api.models.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	@Query("SELECT t from Transaction t where year(t.date) = :year " + "and month(t.date) = :month "
			+ "and t.user.id = :userId order by t.date desc")
	List<Transaction> findByMonthYearAndUser(@Param("year") Integer year, @Param("month") Integer month,
			@Param("userId") Long userId);
	
	@Query("SELECT t from Transaction t where year(t.date) = :year and t.user.id = :userId order by t.date desc")
	List<Transaction> findByYearAndUser(@Param("year") Integer year, @Param("userId") Long userId);
}
