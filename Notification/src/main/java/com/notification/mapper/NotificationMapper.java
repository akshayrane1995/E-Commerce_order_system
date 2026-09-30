package com.notification.mapper;

import com.notification.dto.NotificationDto;
import com.notification.entity.Notification;

public class NotificationMapper {

	public static Notification mapToNotification(NotificationDto notificationDto) {
		
		Notification notification = new Notification(
				null,
				notificationDto.userId(),
				notificationDto.orderId(),
				notificationDto.message(),
				notificationDto.type(),
				notificationDto.status(),
				null,
				null);
		return notification;
	}
	
	
	public static NotificationDto mapToNotificationDto(Notification notification) {
		
		NotificationDto notificationDto = new NotificationDto(
				notification.getId(),
				notification.getUserId(),
				notification.getOrderId(),
				notification.getMessage(),
				notification.getType(),
				notification.getStatus(),
				notification.getCreatedAt(),
				notification.getUpdatedAt());
		return notificationDto;
	}
}

