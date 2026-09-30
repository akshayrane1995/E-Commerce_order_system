package com.notification.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.notification.constant.NotificationStatus;
import com.notification.dto.NotificationDto;
import com.notification.entity.Notification;
import com.notification.exception.ResourceNotFoundException;
import com.notification.mapper.NotificationMapper;
import com.notification.repository.NotificationRepository;

@Service
public class NotificationServiceImpl implements NotificationService {

	private NotificationRepository notificationRepository;

	public NotificationServiceImpl(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}

	@Override
	public NotificationDto createNotification(NotificationDto notificationDto) {
		Notification notification = NotificationMapper.mapToNotification(notificationDto);
		LocalDateTime now = LocalDateTime.now();
		notification.setStatus(NotificationStatus.PENDING);   
		notification.setCreatedAt(now);
		notification.setUpdatedAt(now);
		Notification save = notificationRepository.save(notification);
		return NotificationMapper.mapToNotificationDto(save);
	}

	@Override
	public NotificationDto getNotificationById(Long id) {
		Notification notification = notificationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Notification does not exists"));
		return NotificationMapper.mapToNotificationDto(notification);
	}

	@Override
	public List<NotificationDto> getAllNotification() {
		List<Notification> allNotification = notificationRepository.findAll();
		return allNotification.stream().map((notifications) -> NotificationMapper.mapToNotificationDto(notifications))
				.collect(Collectors.toList());
	}

	@Override
	public NotificationDto updateNotification(NotificationDto notificationDto, Long id) {
		Notification notification = notificationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("notification does not exists"));

		notification.setMessage(notificationDto.message());
		notification.setOrderId(notificationDto.orderId());
		notification.setType(notificationDto.type());
		notification.setUserId(notificationDto.userId());
		notification.setUpdatedAt(LocalDateTime.now());
		if (notificationDto.status() != null) {
		    notification.setStatus(notificationDto.status());
		}

		Notification update = notificationRepository.save(notification);
		return NotificationMapper.mapToNotificationDto(update);
	}

	@Override
	public void deleteNotificationById(Long id) {
		if (!notificationRepository.existsById(id)) {
			throw new ResourceNotFoundException("Notification does not exists");
		}
		notificationRepository.deleteById(id);
	}

}
