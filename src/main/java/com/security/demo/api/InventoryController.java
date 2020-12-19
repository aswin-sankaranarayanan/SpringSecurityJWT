package com.security.demo.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.demo.entity.Inventory;
import com.security.demo.services.InventoryService;

@RestController
@RequestMapping("/api/items")
public class InventoryController {

	private InventoryService inventoryService;
	
	public InventoryController(InventoryService inventoryService) {
		super();
		this.inventoryService = inventoryService;
	}

	@GetMapping
	public Iterable<Inventory> getAllItems(){
		return inventoryService.getAllItems();
	}
	
}
