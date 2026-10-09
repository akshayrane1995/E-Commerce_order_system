package com.payment.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.payment.event.PaymentResultEvent;

@Service
public class PaymentEventProducer {

	private final KafkaTemplate<String, PaymentResultEvent> kafkaTemplate;

	public PaymentEventProducer(KafkaTemplate<String, PaymentResultEvent> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void publishPaymentResult(PaymentResultEvent event) {

		String topic = event.status().equals("SUCCESS") ? "payment-completed" : "payment-failed";

		kafkaTemplate.send(topic, event);

		System.out.println("Payment result published to " + topic + ": " + event);
	}
}