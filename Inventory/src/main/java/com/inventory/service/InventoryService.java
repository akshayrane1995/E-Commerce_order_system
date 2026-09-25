package com.inventory.service;

import java.util.List;

import com.inventory.dto.InventoryDto;

public interface InventoryService {

	InventoryDto createInventory(InventoryDto inventoryDto);

	InventoryDto getInventoryById(Long id);

	List<InventoryDto> getAllInventory();

	InventoryDto updateInventory(Long id, InventoryDto inventoryDto);

	void deleteInventory(Long id);

	
}
