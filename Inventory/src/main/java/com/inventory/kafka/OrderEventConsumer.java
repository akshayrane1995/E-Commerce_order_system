package com.inventory.kafka;

import com.inventory.event.OrderCreatedEvent;
import com.inventory.event.OrderItemEvent;
import com.inventory.service.InventoryService;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {

	private final InventoryService inventoryService;

	public OrderEventConsumer(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	@KafkaListener(topics = "order-created", groupId = "inventory-group")
	public void consumeOrderCreated(OrderCreatedEvent event) {

		for (OrderItemEvent item : event.items()) {
			inventoryService.reserveStock(item.productId(), item.quantity());
		}

		System.out.println("Inventory Service received order: " + event);
	}
}