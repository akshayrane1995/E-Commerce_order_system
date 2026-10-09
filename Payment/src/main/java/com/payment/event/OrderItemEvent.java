package com.payment.event;

public record OrderItemEvent(
        Long productId,
        Integer quantity
) {}