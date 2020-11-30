package com.fortunator.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fortunator.api.models.Level;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long>{

}
