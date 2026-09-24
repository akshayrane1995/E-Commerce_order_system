package com.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long>{

}
