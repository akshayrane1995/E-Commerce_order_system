package com.order.dto;

import java.math.BigDecimal;

public record ProductResponseDto(
        Long id,
        String name,
        BigDecimal price,
        Integer stockQuantity
) {
}