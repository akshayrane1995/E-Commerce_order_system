package com.order.event;

public record OrderItemEvent(
        Long productId,
        Integer quantity
) {}