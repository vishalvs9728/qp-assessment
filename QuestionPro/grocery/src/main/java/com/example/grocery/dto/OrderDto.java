package com.example.grocery.dto;

import java.util.List;

import com.example.grocery.model.Grocery;

import lombok.Data;
@Data
public class OrderDto {
	private List<Grocery> groceries;
}
