package com.order.event;

import java.math.BigDecimal;

public record PaymentResultEvent(Long orderId, Long userId, BigDecimal amount, String status, String message) {
}
