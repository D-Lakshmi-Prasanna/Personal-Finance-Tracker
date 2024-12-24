package com.example.finance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.finance.entity.Category;
import com.example.finance.entity.FinancialSummary;
import com.example.finance.entity.Transaction;
import com.example.finance.services.CategoryService;
import com.example.finance.services.FinancialSummaryService;
import com.example.finance.services.TransactionService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	@Autowired
    private CategoryService categoryService;
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private FinancialSummaryService financialSummaryService;


	@GetMapping("/all/categories")
    public ResponseEntity<?> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            System.out.println(categories);
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error fetching categories");
        }
    }
	
	@GetMapping("/all/transactions")
    public ResponseEntity<?> getAllTransactions() {
        try {
            List<Transaction> transactions = transactionService.getAllTransactions();
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error fetching transactions");
        }
    }
	
    @GetMapping("/summaries")
    public ResponseEntity<?> getSummaries() {
        try {
            List<FinancialSummary> summaries = financialSummaryService.getSummaries();
            return ResponseEntity.ok(summaries);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error fetching summary");
        }
    }
}
