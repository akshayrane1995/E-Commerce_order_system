package com.notification.event;

import java.math.BigDecimal;

public record OrderCreatedEvent(
        Long orderId,
        Long userId,
        BigDecimal totalAmount
) {}