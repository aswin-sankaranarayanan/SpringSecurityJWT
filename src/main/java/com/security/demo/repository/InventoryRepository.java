package com.security.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.security.demo.entity.Inventory;

@Repository
public interface InventoryRepository extends CrudRepository<Inventory, Long> {

}
