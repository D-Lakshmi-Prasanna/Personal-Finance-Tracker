package com.example.finance.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.finance.entity.AuthUser;
import com.example.finance.entity.Category;
import com.example.finance.entity.Transaction;
import com.example.finance.exceptions.CustomExceptions;
import com.example.finance.exceptions.EntityNotFoundException;
import com.example.finance.exceptions.ExpenseNegativeException;
import com.example.finance.exceptions.IncomeNegativeException;
import com.example.finance.exceptions.InvalidTypeTransactionException;
import com.example.finance.exceptions.UserNotFoundException;
import com.example.finance.repository.CategoryRepository;
import com.example.finance.repository.TransactionRepository;
import com.example.finance.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/*
 * author: Prasanna
 * 
 * TransactionService handles business logic related to transactions.
 * It interacts with the TransactionRepository and UserRepository to manage transaction data for users.
 */
@Slf4j
@Service
public class TransactionService {

    // Injected TransactionRepository for managing transaction data
    @Autowired
    private TransactionRepository transactionRepository;

    // Injected UserRepository for managing user data
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
	private CategoryRepository categoryRepository;

    /*
     * author: Prasanna
     * 
     * Adds a new transaction for a user.
     * 
     * @param transaction the Transaction object containing transaction details
     * @return Transaction object after it has been saved to the repository
     * @throws UserNotFoundException if the associated user is not found
     */
    public Transaction addTransaction(Transaction transaction) {
    	switch(transaction.getType()) {
	    	case INCOME:
	    		if(transaction.getAmount()>=0) {
	    			transaction=addFields(transaction);
	    			return transactionRepository.save(transaction);
	    		}
	    		else {
	    			log.info("Income should be greater than 0");
	    			throw new IncomeNegativeException("Income should be greater than 0");
	    		}
	    	case EXPENSE:
	    		if(transaction.getAmount()<=0) {
	    			transaction=addFields(transaction);
	    			return transactionRepository.save(transaction);
	    		}
	    		else {
	    			log.info("Expense should be less than 0");
	    			throw new ExpenseNegativeException("Expense should be less than 0");
	    		}
	    	default:
	    		log.info("Type should be INCOME or Expense");
	    		throw new InvalidTypeTransactionException("Type should be INCOME or Expense");
	    }
    		
    			
        
    }
    public Transaction addFields(Transaction transaction) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        AuthUser user=getUserByEmail(email);
        transaction.setUser(user);
        Long cId=transaction.getCategory().getId();
        Category category=categoryRepository.findById(cId).orElseThrow(()-> new UserNotFoundException("Category id not exists"));
        transaction.setCategory(category);
        return transaction;
    }

	public AuthUser getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email)); // You can use your custom exception or return null
    }
	
	/*
     * author: Prasanna
     * 
     * Retrieves a transaction by its ID.
     * 
     * @param id the ID of the transaction
     * @return Optional containing the Transaction object if found
     * @throws EntityNotFoundException if the transaction is not found
     */
    public List<Transaction> getTransactions() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        AuthUser user=getUserByEmail(email);
        Long id=user.getId();
        return transactionRepository.findAllByUser_Id(id);
    }

    /*
     * author: Prasanna
     * 
     * Updates an existing transaction.
     * 
     * @param transactionId the ID of the transaction to be updated
     * @param updatedTransaction the Transaction object containing updated details
     * @return Transaction object after it has been updated
     * @throws EntityNotFoundException if the transaction is not found
     */
    public Transaction updateTransaction(Long transactionId, Transaction updatedTransaction) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        AuthUser user=getUserByEmail(email);
        Long id=user.getId();
        Transaction transaction=transactionRepository.findById(transactionId).orElseThrow(() -> new EntityNotFoundException("Transaction not found"));;
        Long userId=transaction.getUser().getId();
        if(id==userId) {
	        transaction.setAmount(updatedTransaction.getAmount());
	//      transaction.setType(updatedTransaction.getType());
	//      transaction.setDate(updatedTransaction.getDate());
	        return transactionRepository.save(transaction);
        }
        else {
        	log.info("You cannot update this transaction because this transaction is not belongs to you");
        	throw new CustomExceptions("You cannot update this transaction because this transaction is not belongs to you");
        }
    }

    /*
     * author: Prasanna
     * 
     * Deletes a transaction by its ID.
     * 
     * @param transactionId the ID of the transaction to be deleted
     * @throws EntityNotFoundException if the transaction is not found
     */
    public void deleteTransaction(Long transactionId) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        AuthUser user=getUserByEmail(email);
        Long id=user.getId();
        Transaction transaction=transactionRepository.findById(transactionId).orElseThrow(() -> new EntityNotFoundException("Transaction not found"));;
        Long userId=transaction.getUser().getId();
        if(id==userId) {
	        transactionRepository.delete(transaction);
        }
        else {
        	log.info("You cannot delete this transaction because this transaction is not belongs to you");
        }
    }
	public List<Transaction> getAllTransactions() {
		// TODO Auto-generated method stub
		return transactionRepository.findAll();
	}
}
