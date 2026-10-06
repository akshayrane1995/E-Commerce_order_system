package com.notification.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.notification.event.OrderCreatedEvent;

@Service
public class OrderEventConsumer {

    @KafkaListener(
            topics = "order-created",
            groupId = "notification-group"
    )
    public void consumeOrderCreated(OrderCreatedEvent event) {

        System.out.println(
                "Inventory Service received order: " + event
        );
    }
}