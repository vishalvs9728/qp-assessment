package com.example.grocery.exception;

public class StockNotAvailable extends RuntimeException{

	
	public StockNotAvailable(String message) {
		super(message);
	}

}
