package com.fortunator.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fortunator.api.models.Balance;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long>{

	Optional<Balance> findByUserId(Long userId);

}
