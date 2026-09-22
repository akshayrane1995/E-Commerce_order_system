package com.payment.dto;

import java.math.BigDecimal;

import java.time.LocalDateTime;

import com.payment.constant.PaymentStatus;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record PaymentDto(
					Long id, 
					
					@NotNull(message = "Order id is required")
					Long orderId, 
					
					@NotNull(message = "Amount is required")
			        @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
					BigDecimal amount, 
					
					
					PaymentStatus status,
					LocalDateTime createdAt, 
					LocalDateTime updatedAt) {

}
