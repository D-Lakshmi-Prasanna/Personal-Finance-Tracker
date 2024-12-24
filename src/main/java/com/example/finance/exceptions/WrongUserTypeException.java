package com.example.finance.exceptions;

public class WrongUserTypeException extends RuntimeException{
	public WrongUserTypeException(String meassage) {
		super(meassage);
	}
}
