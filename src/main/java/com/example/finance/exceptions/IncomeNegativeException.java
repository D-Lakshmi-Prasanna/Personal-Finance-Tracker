package com.example.finance.exceptions;

public class IncomeNegativeException extends RuntimeException {
	public IncomeNegativeException(String message) {
		super(message);
	}
}
