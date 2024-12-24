package com.example.finance.exceptions;

public class InvalidTypeTransactionException extends RuntimeException {
	public InvalidTypeTransactionException(String message) {
		super(message);
	}
}
