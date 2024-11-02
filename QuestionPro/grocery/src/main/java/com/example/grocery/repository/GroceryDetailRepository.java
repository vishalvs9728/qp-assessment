package com.example.grocery.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.grocery.model.GroceryDetails;

@Repository
public interface GroceryDetailRepository extends JpaRepository<GroceryDetails, Long>{

	Optional<GroceryDetails> findByName(String name);
	List<GroceryDetails> findByQuantityGreaterThan(long quantity);
}
