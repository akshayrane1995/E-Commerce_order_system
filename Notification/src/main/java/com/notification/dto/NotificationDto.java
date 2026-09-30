package com.notification.dto;

import java.time.LocalDateTime;

import com.notification.constant.NotificationStatus;
import com.notification.constant.NotificationType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NotificationDto(
        Long id,

        @NotNull(message = "User id is required")
        Long userId,

        @NotNull(message = "Order id is required")
        Long orderId,

        @NotBlank(message = "Message is required")
        String message,

        @NotNull(message = "Notification type is required")
        NotificationType type,

        NotificationStatus status,

        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}