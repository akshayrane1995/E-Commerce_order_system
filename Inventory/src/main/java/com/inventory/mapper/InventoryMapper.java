package com.inventory.mapper;

import com.inventory.dto.InventoryDto;
import com.inventory.entity.Inventory;

public class InventoryMapper {

	public static Inventory mapToInventory(InventoryDto inventoryDto){
		
		Inventory inventory = new Inventory(
				null,
				inventoryDto.productId(),
				inventoryDto.availableQuantity(),
				inventoryDto.reservedQuantity(),
				null,
				null);
		
		return inventory;
	}
	
	public static InventoryDto mapToInventoryDto(Inventory inventory){
		
		InventoryDto inventoryDto = new InventoryDto(
				inventory.getId(),
				inventory.getProductId(),
				inventory.getAvailableQuantity(),
				inventory.getReservedQuantity(),
				inventory.getCreatedAt(),
				inventory.getUpdatedAt());
		
		return inventoryDto;
	}
}
