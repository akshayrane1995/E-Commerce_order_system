package com.inventory.kafka;

import com.inventory.event.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {

    @KafkaListener(
            topics = "order-created",
            groupId = "inventory-group"
    )
    public void consumeOrderCreated(OrderCreatedEvent event) {

        System.out.println(
                "Inventory Service received order: " + event
        );
    }
}