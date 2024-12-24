package com.example.finance.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.finance.entity.Transaction;
import com.example.finance.services.TransactionService;

/*
 * author: Prasanna
 * 
 * TransactionController handles operations related to transactions.
 * It provides endpoints to add, retrieve, update, and delete transactions for users.
 */
@RestController
@RequestMapping("/api/user")
public class TransactionController {

    // Injected TransactionService for handling transaction operations
    @Autowired
    private TransactionService transactionService;

    /*
     * author: Prasanna
     * 
     * Adds a new transaction to the system.
     * 
     * @param transaction the transaction details in the request body
     * @return ResponseEntity containing the saved transaction or an error message
     */
    @PostMapping("/transaction")
    public ResponseEntity<?> addTransaction(@RequestBody Transaction transaction) {
        try {
            Transaction savedTransaction = transactionService.addTransaction(transaction);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTransaction);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error adding transaction");
        }
    }
    
    /*
     * author: Prasanna
     * 
     * Fetches all transactions for a specific user based on user ID.
     * 
     * @param userId the ID of the user whose transactions are to be fetched
     * @return ResponseEntity containing the list of transactions for the user
     */
    @GetMapping("/transactions")
    public ResponseEntity<?> getTransactions() {
        try {
            List<Transaction> transactions = transactionService.getTransactions();
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error fetching transactions");
        }
    }
    
    /*
     * author: Prasanna
     * 
     * Updates an existing transaction based on the provided transaction ID and new data.
     * 
     * @param id the ID of the transaction to be updated
     * @param transaction the updated transaction details in the request body
     * @return ResponseEntity containing the updated transaction or an error message
     */
    @PutMapping("/transaction/{id}")
    public ResponseEntity<?> updateTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
        try {
            Transaction updatedTransaction = transactionService.updateTransaction(id, transaction);
            return ResponseEntity.ok(updatedTransaction);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating transaction");
        }
    }
    
    /*
     * author: Prasanna
     * 
     * Deletes a transaction based on its ID.
     * 
     * @param id the ID of the transaction to be deleted
     * @return ResponseEntity indicating successful deletion or an error message
     */
    @DeleteMapping("/transaction/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id) {
        try {
            transactionService.deleteTransaction(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error deleting transaction");
        }
    }
}
