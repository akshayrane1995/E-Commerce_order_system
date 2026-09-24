package com.inventory.dto;

import java.time.LocalDateTime;

public record InventoryDto(
		Long id,
		Long productId,
		Long availableQuantity,
		Long reservedQuantity,
		LocalDateTime createdAt,
		LocalDateTime updatedAt) {

}
