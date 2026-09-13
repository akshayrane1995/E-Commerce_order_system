package com.product.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductDto(
		Long id,
		
		@NotBlank(message = "Product name is required")
        String name, 
        
        @Size(max = 500, message = "Description must not exceed 500 characters")
		String description, 
		
		@NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
		BigDecimal price, 
		
		@NotBlank(message = "Category is required")
		String category, 
		
		@NotNull(message = "Stock quantity is required")
        @Min(value = 0, message = "Stock quantity cannot be negative")
		Integer stockQuantity,
		
		LocalDateTime createdAt, 
		LocalDateTime updatedAt) {}
