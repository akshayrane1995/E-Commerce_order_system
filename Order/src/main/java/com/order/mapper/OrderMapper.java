package com.order.mapper;

import java.util.List;

import com.order.dto.OrderDto;
import com.order.dto.OrderItemDto;
import com.order.entity.Order;
import com.order.entity.OrderItem;

public class OrderMapper {

	public static Order mapToOrder(OrderDto orderDto) {

		Order order = new Order(
				orderDto.id(),
				orderDto.userId(),
				orderDto.totalAmount(),
				orderDto.status(),
				orderDto.createdAt(),
				orderDto.updatedAt(),
				null);

		List<OrderItem> items = orderDto.items()
				.stream()
				.map(itemDto -> mapToOrderItem(itemDto, order))
				.toList();

		order.setItem(items);

		return order;
	}

	public static OrderDto mapToOrderDto(Order order) {

		List<OrderItemDto> items = order.getItem()
				.stream()
				.map(OrderMapper::mapToOrderItemDto)
				.toList();

		OrderDto orderDto = new OrderDto(
				order.getId(),
				order.getUserId(),
				order.getTotalAmount(),
				order.getStatus(),
				items,
				order.getCreatedAt(),
				order.getUpdatedAt());

		return orderDto;
	}

	private static OrderItem mapToOrderItem(OrderItemDto itemDto, Order order) {

		OrderItem item = new OrderItem(
				itemDto.id(),
				itemDto.productId(),
				itemDto.quantity(),
				itemDto.price(),
				itemDto.subtotal(),
				order);

		return item;
	}

	private static OrderItemDto mapToOrderItemDto(OrderItem item) {

		return new OrderItemDto(
				item.getId(),
				item.getProductId(),
				item.getQuantity(),
				item.getPrice(),
				item.getSubtotal());
	}
}