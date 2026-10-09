package com.payment.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.payment.event.OrderCreatedEvent;
import com.payment.service.PaymentService;

@Service
public class OrderEventConsumer {

	private final PaymentService paymentService;

	public OrderEventConsumer(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@KafkaListener(topics = "order-created", groupId = "payment-group")
	public void consumeOrderCreated(OrderCreatedEvent event) {

		System.out.println("Order created event received: " + event);

		paymentService.processPayment(event.orderId(), event.userId(), event.totalAmount());
	}
}