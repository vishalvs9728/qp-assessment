package com.example.grocery.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GroceryNotFound extends RuntimeException {


	public GroceryNotFound(String message) {
		super(message);
	}

}
