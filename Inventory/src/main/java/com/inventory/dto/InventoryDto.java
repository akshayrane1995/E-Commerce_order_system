package com.inventory.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record InventoryDto(
        Long id,

        @NotNull(message = "Product id is required")
        Long productId,

        @NotNull(message = "Available quantity is required")
        @Min(value = 0, message = "Available quantity cannot be negative")
        Long availableQuantity,

        @NotNull(message = "Reserved quantity is required")
        @Min(value = 0, message = "Reserved quantity cannot be negative")
        Long reservedQuantity,

        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}