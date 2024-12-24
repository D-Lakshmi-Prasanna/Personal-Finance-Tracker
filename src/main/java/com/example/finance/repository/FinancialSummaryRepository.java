package com.example.finance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.finance.entity.FinancialSummary;

/*
 * author: Prasanna
 * 
 * FinancialSummaryRepository is a JPA repository interface for managing FinancialSummary entities.
 * It extends JpaRepository to provide basic CRUD operations.
 */
public interface FinancialSummaryRepository extends JpaRepository<FinancialSummary, Long> {
	
	@Query("Select f from FinancialSummary f where f.user.id=:id")
	Optional<FinancialSummary> findByUser_Id(@Param("id")Long id);
	
}
