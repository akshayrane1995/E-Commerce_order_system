package com.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.payment.constant.PaymentStauts;

public record PaymentDto(Long id, Long orderID, BigDecimal amount, PaymentStauts status,
						LocalDateTime createdAt, LocalDateTime updatedAt) {

}
