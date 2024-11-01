package com.example.grocery.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "grocery")
public class Grocery {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private long groceryId;
	private long quantity;
	
	@ManyToOne()
	@JoinColumn(name = "orders_id")
	@JsonIgnore
	private Orders orders;
}
