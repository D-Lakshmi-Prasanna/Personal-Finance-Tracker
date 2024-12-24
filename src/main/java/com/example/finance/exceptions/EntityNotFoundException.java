package com.example.finance.exceptions;

/*
 * author: Prasanna
 * 
 * EntityNotFoundException is a custom runtime exception class used to handle scenarios where a requested entity does not exist.
 */
public class EntityNotFoundException extends RuntimeException {
    /*
     * Constructs a new EntityNotFoundException with the specified detail message.
     * 
     * @param message the detail message explaining the reason for the exception
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
