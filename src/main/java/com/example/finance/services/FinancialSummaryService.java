package com.example.finance.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.finance.entity.AuthUser;
import com.example.finance.entity.FinancialSummary;
import com.example.finance.entity.Transaction;
import com.example.finance.exceptions.UserNotFoundException;
import com.example.finance.repository.FinancialSummaryRepository;
import com.example.finance.repository.TransactionRepository;
import com.example.finance.repository.UserRepository;
import com.example.finance.util.Type;

import lombok.extern.slf4j.Slf4j;

/*
 * author: Prasanna
 * 
 * FinancialSummaryService handles the business logic related to financial summaries.
 * It interacts with the TransactionRepository to perform operations on transactions and calculate financial summaries.
 */
@Slf4j
@Service
public class FinancialSummaryService {

//    @Autowired
//    private FinancialSummaryRepository financialSummaryRepository;
//
//    Injected TransactionRepository for managing transaction data
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
	private FinancialSummaryRepository financialSummaryRepository;

//    public FinancialSummary getFinancialSummary(String username, Long userId) {
//        Double totalIncome = transactionRepository.sumIncomeByUsername(username);
//        Double totalExpense = transactionRepository.sumExpenseByUsername(username);
//        Double totalBalance = totalIncome - totalExpense;
//
//        FinancialSummary summary = new FinancialSummary();
//        summary.setId(userId);
//        summary.setTotalIncome(totalIncome);
//        summary.setTotalExpense(totalExpense);
//        summary.setTotalBalance(totalBalance);
//
//        return financialSummaryRepository.save(summary);
//    }
    
    /*
     * author: Prasanna
     * 
     * Retrieves the financial summary for a user based on their transaction records.
     * 
     * @param id the ID of the user
     * @return a FinancialSummary object containing total income, total expense, and total balance
     */
    public FinancialSummary getSummary() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        AuthUser user=getUserByEmail(email);
        Long id=user.getId();
        List<Transaction> transactions=transactionRepository.findAllByUser_Id(id);
        System.out.println(transactions);
        double totalIncome = transactions.stream().filter(t -> t.getType()==Type.INCOME).mapToDouble(Transaction::getAmount).sum();
        double totalExpense = transactions.stream().filter(t -> t.getType()==Type.EXPENSE).mapToDouble(Transaction::getAmount).sum();
        double totalBalance = totalIncome + totalExpense;
        System.out.println(totalIncome);
        System.out.println(totalExpense);
        System.out.println(totalBalance);
        FinancialSummary summary = financialSummaryRepository.findByUser_Id(id).orElse(new FinancialSummary());
        summary.setUser(user);
        summary.setTotalIncome(totalIncome);
        summary.setTotalExpense(totalExpense);
        summary.setTotalBalance(totalBalance);

        return financialSummaryRepository.save(summary);
    }
	public AuthUser getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email)); // You can use your custom exception or return null
    }
	public List<FinancialSummary> getSummaries() {		
		return financialSummaryRepository.findAll();
	}
	
}
