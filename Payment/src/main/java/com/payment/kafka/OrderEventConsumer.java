package com.payment.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.payment.event.OrderCreatedEvent;

@Service
public class OrderEventConsumer {

	@KafkaListener(topics = "order-created" , groupId = "payment-group")
	
	public void consumeOrderCreated(OrderCreatedEvent event) {
		System.out.println("Order created event received " + event);
	}
}
