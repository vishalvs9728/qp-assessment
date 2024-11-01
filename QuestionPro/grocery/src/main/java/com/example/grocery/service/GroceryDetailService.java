package com.example.grocery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.grocery.exception.GroceryAlreadyExist;
import com.example.grocery.exception.GroceryNotFound;
import com.example.grocery.model.GroceryDetails;
import com.example.grocery.repository.GroceryDetailRepository;
import com.example.grocery.repository.UserRepository;

@Service
public class GroceryDetailService {

	@Autowired
	private GroceryDetailRepository groceryDetailRepository;

	public void addGrocery(GroceryDetails groceryDetail) {
		GroceryDetails detail = groceryDetailRepository.findByName(groceryDetail.getName()).orElse(null);
		if(detail!=null) {
			throw new GroceryAlreadyExist("Grocery already exist with name "+ groceryDetail.getName());
		}
		groceryDetailRepository.save(groceryDetail);
		
	}

	public GroceryDetails getById(long id) {
		GroceryDetails groceryDetail = groceryDetailRepository.findById(id).orElse(null);
		if(groceryDetail==null) {
			throw new GroceryNotFound("grocery does not exist with id "+id);
		}
		return groceryDetail;
	}

	public List<GroceryDetails> getAll() {
		return groceryDetailRepository.findAll();
	}

	public void update(long id, GroceryDetails groceryDetail) {
		GroceryDetails detail = groceryDetailRepository.findById(id).orElse(null);
		if(detail==null) {
			throw new GroceryNotFound("grocery does not exist with id "+id);
		}
		if(!detail.getName().equalsIgnoreCase(groceryDetail.getName()) && groceryDetailRepository.findByName(groceryDetail.getName()).orElse(null)!=null) {
			throw new GroceryAlreadyExist("Grocery already exist with name "+ groceryDetail.getName());
		}
		detail.setPrice(groceryDetail.getPrice());
		detail.setQuantity(groceryDetail.getQuantity());
		detail.setName(groceryDetail.getName());
		groceryDetailRepository.save(detail);
	}

	public void delete(long id) {
		GroceryDetails detail = groceryDetailRepository.findById(id).orElse(null);
		if(detail==null) {
			throw new GroceryNotFound("grocery does not exist with id "+id);
		}
		groceryDetailRepository.deleteById(id);
	}
}
