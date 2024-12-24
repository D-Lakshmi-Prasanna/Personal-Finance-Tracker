package com.example.finance.exceptions;

public class ExpenseNegativeException extends RuntimeException {
	public ExpenseNegativeException(String message) {
		super(message);
	}
}
