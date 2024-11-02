package com.example.grocery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.grocery.model.GroceryDetails;
import com.example.grocery.service.GroceryDetailService;

@RestController
@RequestMapping("/grocery")
public class GroceryController {
	
	@Autowired
	private GroceryDetailService groceryDetailService;

	@PostMapping("/add")
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasRole('ADMIN')")
	public void addGrocery(@RequestBody GroceryDetails groceryDetail) {
		groceryDetailService.addGrocery(groceryDetail);
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasRole('ADMIN')")
	public GroceryDetails getById(@PathVariable long id) {
		return groceryDetailService.getById(id);
	}
	
	@GetMapping("/all")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasRole('ADMIN')")
	public List<GroceryDetails> getAll() {
		return groceryDetailService.getAll();
	}
	
	
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasRole('ADMIN')")
	public void update(@PathVariable long id, @RequestBody GroceryDetails groceryDetail) {
		groceryDetailService.update(id,groceryDetail);
	}
	
	
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasRole('ADMIN')")
	public void delete(@PathVariable long id) {
		groceryDetailService.delete(id);
	}
	
	
}
