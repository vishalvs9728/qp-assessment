package com.example.grocery.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GroceryAlreadyExist extends RuntimeException{



	public GroceryAlreadyExist(String message) {
		super(message);
	}

}
