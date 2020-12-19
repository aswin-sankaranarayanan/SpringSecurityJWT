package com.security.demo.services;

import org.springframework.stereotype.Service;

import com.security.demo.entity.Inventory;
import com.security.demo.repository.InventoryRepository;;

@Service
public class InventoryService {

	InventoryRepository repository;
	
	public InventoryService(InventoryRepository repository) {
		super();
		this.repository = repository;
	}

	public Iterable<Inventory> getAllItems(){
		return repository.findAll();
		
	}
}
