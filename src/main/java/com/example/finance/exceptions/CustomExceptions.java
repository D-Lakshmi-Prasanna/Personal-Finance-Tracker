package com.example.finance.exceptions;

/*
 * author: Prasanna
 * 
 * CustomExceptions is a generic runtime exception class used to handle custom error messages.
 */
public class CustomExceptions extends RuntimeException {
    /*
     * Constructs a new CustomExceptions with the specified detail message.
     * 
     * @param message the detail message explaining the reason for the exception
     */
    public CustomExceptions(String message) {
        super(message);
    }
}
