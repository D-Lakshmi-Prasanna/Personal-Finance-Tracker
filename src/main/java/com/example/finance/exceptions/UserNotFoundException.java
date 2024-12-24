package com.example.finance.exceptions;

public class UserNotFoundException extends RuntimeException {

    /*
     * author: Prasanna
     * 
     * UserNotFoundException is a custom exception used to indicate that a specific user was not found.
     * It extends the RuntimeException class.
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
