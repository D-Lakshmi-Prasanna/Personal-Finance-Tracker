package com.example.finance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.finance.entity.FinancialSummary;
import com.example.finance.services.FinancialSummaryService;

/*
 * author: Prasanna
 * 
 * FinancialSummaryController handles operations related to financial summaries.
 * It interacts with the FinancialSummaryService to fetch summary data for a user.
 */
@RestController
@RequestMapping("/api/user")
public class FinancialSummaryController {
    // Injected FinancialSummaryService for handling summary operations
    @Autowired
    private FinancialSummaryService financialSummaryService;

    /*
     * author: Prasanna
     * 
     * Fetches the financial summary for a given user ID.
     * 
     * @param userId the ID of the user whose financial summary is to be fetched
     * @return ResponseEntity containing the financial summary or an error message
     */
    @GetMapping("/summary")
    public ResponseEntity<?> getSummary() {
        try {
            FinancialSummary summary = financialSummaryService.getSummary();
            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error fetching summary");
        }
    }

}
