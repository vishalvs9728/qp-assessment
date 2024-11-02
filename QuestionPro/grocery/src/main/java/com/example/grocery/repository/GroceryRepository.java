package com.example.grocery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.grocery.model.Grocery;

@Repository
public interface GroceryRepository extends JpaRepository<Grocery, Long>{

}
