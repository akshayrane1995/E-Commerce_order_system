package com.notification.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notification.dto.NotificationDto;
import com.notification.service.NotificationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/notification")
public class NotificationController {

	private NotificationService notificationService;

	public NotificationController(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@PostMapping("/create")
	public ResponseEntity<NotificationDto> createNotification(@Valid @RequestBody NotificationDto notificationDto) {

		NotificationDto save = notificationService.createNotification(notificationDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(save);
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<NotificationDto> getNotificationById(@PathVariable Long id) {
		NotificationDto notificationDto = notificationService.getNotificationById(id);
		return ResponseEntity.ok(notificationDto);
	}

	@GetMapping
	public ResponseEntity<List<NotificationDto>> getAllNotification() {
		List<NotificationDto> allNotification = notificationService.getAllNotification();
		return ResponseEntity.ok(allNotification);
	}
	
	@PutMapping("/id/{id}")
	public ResponseEntity<NotificationDto> updateNotitfication(@Valid @RequestBody NotificationDto notificationDto, @PathVariable Long id){
		NotificationDto updatedNotification = notificationService.updateNotification(notificationDto,id);
		return ResponseEntity.ok(updatedNotification);
	}
	
	@DeleteMapping("/id/{id}")
	public ResponseEntity<String> deleteNotificationById(@PathVariable Long id){
		notificationService.deleteNotificationById(id);
		return ResponseEntity.ok("Notification is deleted");
	}
}
