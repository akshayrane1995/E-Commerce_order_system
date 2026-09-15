package com.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.order.constant.OrderStatus;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record OrderDto(
		 	Long id,
		 	
		 	@NotNull(message = "user id is required")
	        Long userId,
	        BigDecimal totalAmount,
	        OrderStatus status,
	        
	        @NotEmpty(message = "At least 1 item is required")
			@Valid
	        List<OrderItemDto> items,
	        LocalDateTime createdAt,
	        LocalDateTime updatedAt) {}
