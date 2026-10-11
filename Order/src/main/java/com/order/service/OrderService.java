package com.order.service;

import com.order.constant.OrderStatus;
import com.order.dto.OrderDto;

public interface OrderService {

	OrderDto createOrder(OrderDto orderDto);

	OrderDto getOrderById(Long id);

	OrderDto updateOrder(Long id, OrderDto orderDto);

	void deleteOrder(Long id);
	
	void updateOrderStatus(Long orderId, OrderStatus status);

}
