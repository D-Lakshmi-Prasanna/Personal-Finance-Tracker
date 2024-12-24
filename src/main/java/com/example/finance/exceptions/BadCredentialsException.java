package com.example.finance.exceptions;

/*
 * author: Prasanna
 * 
 * BadCredentialsException is a custom exception thrown when invalid credentials are provided during authentication.
 */
public class BadCredentialsException extends RuntimeException {
    /*
     * Constructs a new BadCredentialsException with the specified detail message.
     * 
     * @param message the detail message explaining the reason for the exception
     */
    public BadCredentialsException(String message) {
        super(message);
    }
}
