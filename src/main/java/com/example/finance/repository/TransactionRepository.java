package com.example.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.finance.entity.Transaction;

/*
 * author: Prasanna
 * 
 * TransactionRepository is a JPA repository interface for managing Transaction entities.
 * It extends JpaRepository to provide basic CRUD operations.
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	@Query("SELECT t FROM Transaction t where t.user.id=:id")
	List<Transaction> findAllByUser_Id(@Param("id")Long id);
    
    // List<Transaction> findByUser_Username(String username);
    // @Query("SELECT SUM(t.amount) FROM Transaction t JOIN t.user u WHERE u.username = :username AND t.type = 'INCOME'")
    // Double sumIncomeByUsername(@Param("username") String username);
    // @Query("SELECT SUM(t.amount) FROM Transaction t JOIN t.user u WHERE u.username = :username AND t.type = 'EXPENSE'")
    // Double sumExpenseByUsername(@Param("username") String username);
}
