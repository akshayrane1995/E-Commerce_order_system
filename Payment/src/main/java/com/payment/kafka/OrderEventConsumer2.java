package com.payment.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.payment.event.OrderCreatedEvent;

@Service
public class OrderEventConsumer2 {

	@KafkaListener(topics = "order-created" , groupId = "payment-group")
	
	public void consumerOrderCreated(OrderCreatedEvent event) {
		System.out.println("Consumer 2 received: " + event);
	}
}
