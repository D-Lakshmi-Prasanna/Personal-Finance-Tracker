package com.example.finance.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
 * author: Prasanna
 * 
 * GlobalExceptionHandler is a centralized exception handling mechanism for the application.
 * It handles specific types of exceptions and returns appropriate HTTP status codes and messages.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
     * author: Prasanna
     * Handles CustomExceptions and returns a BAD_REQUEST status with the exception message.
     * 
     * @param ex the CustomExceptions thrown
     * @return ResponseEntity with the exception message and BAD_REQUEST status
     */
    @ExceptionHandler(CustomExceptions.class)
    public ResponseEntity<String> handleCustomExceptions(CustomExceptions ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /*
     * author: Prasanna
     * Handles UserNotFoundException and returns a NOT_FOUND status with the exception message.
     * 
     * @param ex the UserNotFoundException thrown
     * @return ResponseEntity with the exception message and NOT_FOUND status
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /*
     * author: Prasanna
     * Handles EntityNotFoundException and returns a NOT_FOUND status with a default error message.
     * 
     * @param ex the EntityNotFoundException thrown
     * @return ResponseEntity with the default error message and NOT_FOUND status
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongUserTypeException.class)
    public ResponseEntity<String> handleWrongUserTypeException(WrongUserTypeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
    
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
    
    @ExceptionHandler(ExpenseNegativeException.class)
    public ResponseEntity<String> handleExpenseNegativeException(ExpenseNegativeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
    
    @ExceptionHandler(IncomeNegativeException.class)
    public ResponseEntity<String> handleIncomeNegativeException(IncomeNegativeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
    
    @ExceptionHandler(InvalidTypeTransactionException.class)
    public ResponseEntity<String> handleInvalidTypeTransactionException(InvalidTypeTransactionException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
}
