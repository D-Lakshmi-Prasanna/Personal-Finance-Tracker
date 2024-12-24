package com.example.finance.entity;

import java.util.Date;

import com.example.finance.util.Type;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

/*
 * author: Prasanna
 * 
 * Transaction represents a financial transaction for a user.
 * It records the amount, type, date, category, and user associated with the transaction.
 */
@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Unique identifier for the transaction
    private Double amount;  // The amount of the transaction

    @Enumerated(EnumType.STRING)
    private Type type;  // The type of the transaction (e.g., INCOME, EXPENSE)
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date date;  // The date when the transaction occurred

    // Many-to-one relationship with Category, representing the category of the transaction
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // Many-to-one relationship with AuthUser, representing the user associated with this transaction
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AuthUser user;

    // Getters and setters (provided by Lombok's @Data annotation)
}
