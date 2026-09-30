package com.notification.service;

import java.util.List;

import com.notification.dto.NotificationDto;

public interface NotificationService {

	NotificationDto createNotification(NotificationDto notificationDto);

	NotificationDto getNotificationById(Long id);

	List<NotificationDto> getAllNotification();

	NotificationDto updateNotification(NotificationDto notificationDto, Long id);

	void deleteNotificationById(Long id);

}
