
package com.notification.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.notification.constant.NotificationType;
import com.notification.dto.NotificationDto;
import com.notification.event.PaymentResultEvent;
import com.notification.service.NotificationService;

@Service
public class NotificationEventConsumer {

    private final NotificationService notificationService;

    public NotificationEventConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "payment-completed", groupId = "notification-group")
    public void consumePaymentSuccess(PaymentResultEvent event) {
        NotificationDto notification = new NotificationDto(
                null,
                event.userId(),
                event.orderId(),
                "Payment successful for order: " + event.orderId(),
                NotificationType.PAYMENT,
                null,
                null,
                null
        );

        notificationService.createNotification(notification);
        System.out.println("Success notification created for order: " + event.orderId());
    }

    @KafkaListener(topics = "payment-failed", groupId = "notification-group")
    public void consumePaymentFailure(PaymentResultEvent event) {
        NotificationDto notification = new NotificationDto(
                null,
                event.userId(),
                event.orderId(),
                "Payment failed for order: " + event.orderId(),
                NotificationType.PAYMENT,
                null,
                null,
                null
        );

        notificationService.createNotification(notification);
        System.out.println("Failure notification created for order: " + event.orderId());
    }
}
