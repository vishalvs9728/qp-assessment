package com.example.grocery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.grocery.dto.OrderDto;
import com.example.grocery.model.GroceryDetails;
import com.example.grocery.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired 
	private OrderService orderService;
	
	@GetMapping("/available-grocery")
	@PreAuthorize("hasRole('USER')")
	@ResponseStatus(HttpStatus.OK)
	public List<GroceryDetails> getAvailableGrocery(){
		return orderService.getAvailableGrocery();
	}
	
	@PostMapping("/place")
	@PreAuthorize("hasRole('USER')")
	@ResponseStatus(HttpStatus.CREATED)
	public String placeOrder(@RequestBody OrderDto orderDto, @RequestHeader("Authorization") String token) {
		return orderService.placeOrder(orderDto, token);
	}
}
