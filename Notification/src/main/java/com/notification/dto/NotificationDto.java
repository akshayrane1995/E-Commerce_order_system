package com.notification.dto;

import java.time.LocalDateTime;

import com.notification.constant.NotificationStatus;
import com.notification.constant.NotificationType;

public record NotificationDto(
			Long id,
			Long userId,
			Long orderId,
			String mesage,
			NotificationType type,
			NotificationStatus status,
			LocalDateTime createdAt,
			LocalDateTime updatedAt) {}
