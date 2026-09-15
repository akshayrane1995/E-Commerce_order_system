package com.order.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItemDto(
		Long id,
		
		@NotNull(message = "product id is required")
        Long productId,
        
        @NotNull(message = "quantity is required")
		@Min(value = 1, message = "required at least 1 quantity")
        Integer quantity,
        
        @NotNull(message = "price is required")
		@DecimalMin(value = "0.0", inclusive = false, message = "required greater than 0")
        BigDecimal price,
        
        @NotNull(message = "subtotal is required")
		@DecimalMin(value = "0.0", inclusive = false, message = "required greater than 0")
        BigDecimal subtotal) {

}
