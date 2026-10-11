
package com.order.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.order.constant.OrderStatus;
import com.order.event.PaymentResultEvent;
import com.order.service.OrderService;

@Service
public class PaymentResultConsumer {

	private final OrderService orderService;

	public PaymentResultConsumer(OrderService orderService) {
		this.orderService = orderService;
	}

	@KafkaListener(topics = "payment-completed", groupId = "order-group")
	public void consumePaymentSuccess(PaymentResultEvent event) {
		System.out.println("PAYMENT SUCCESS EVENT RECEIVED: " + event);

		orderService.updateOrderStatus(event.orderId(), OrderStatus.CONFIRMED);
	}

	@KafkaListener(topics = "payment-failed", groupId = "order-group")
	public void consumePaymentFailure(PaymentResultEvent event) {
		System.out.println("PAYMENT FAILURE EVENT RECEIVED: " + event);

		orderService.updateOrderStatus(event.orderId(), OrderStatus.PAYMENT_FAILED);
	}
}
