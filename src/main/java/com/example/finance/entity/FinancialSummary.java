package com.example.finance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

/*
 * author: Prasanna
 * 
 * FinancialSummary represents the financial summary data for a user.
 * It contains details about total income, total expenses, and the resulting balance.
 */
@Data
@Entity
public class FinancialSummary {
    
    // Default constructor
    public FinancialSummary() {
    }

    // Parameterized constructor to initialize fields
    public FinancialSummary(Double totalIncome, Double totalExpense, double totalBalance) {
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.totalBalance = totalBalance;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Unique identifier for the financial summary

    private Double totalIncome;  // Total income for the period
    private Double totalExpense; // Total expenses for the period
    private Double totalBalance; // Net balance (income - expenses)

    // One-to-one relationship with AuthUser, representing the user associated with this financial summary
    @OneToOne
    @JoinColumn(name = "user_id")
    private AuthUser user;
}
