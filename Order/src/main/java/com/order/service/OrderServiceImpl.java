package com.order.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.order.client.ProductClient;
import com.order.constant.OrderStatus;
import com.order.dto.OrderDto;
import com.order.dto.ProductResponseDto;
import com.order.entity.Order;
import com.order.entity.OrderItem;
import com.order.exception.ResourceNotFoundException;
import com.order.mapper.OrderMapper;
import com.order.repository.OrderRepository;

import feign.FeignException;

@Service
public class OrderServiceImpl implements OrderService {

	private OrderRepository orderRepository;
	private ProductClient productClient;

	public OrderServiceImpl(OrderRepository orderRepository, ProductClient productClient) {
		this.orderRepository = orderRepository;
		this.productClient = productClient;
	}

	@Override
	public OrderDto createOrder(OrderDto orderDto) {
		Order order = OrderMapper.mapToOrder(orderDto);

		for (OrderItem item : order.getItem()) {
			try {
				ProductResponseDto product = productClient.getProductById(item.getProductId());

				if (product == null) {
					throw new ResourceNotFoundException("Product does not exist: " + item.getProductId());
				}

			} catch (FeignException.NotFound exception) {
				throw new ResourceNotFoundException("Product does not exist: " + item.getProductId());
			}
		}
		LocalDateTime now = LocalDateTime.now();
		order.setCreatedAt(now);
		order.setUpdatedAt(now);
		order.setStatus(OrderStatus.PENDING);
		Order save = orderRepository.save(order);
		return OrderMapper.mapToOrderDto(save);
	}

	@Override
	public OrderDto getOrderById(Long id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("order does not exists"));
		return OrderMapper.mapToOrderDto(order);
	}

	@Override
	public OrderDto updateOrder(Long id, OrderDto orderDto) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("order does not exists"));

		order.setStatus(orderDto.status());
		order.setTotalAmount(orderDto.totalAmount());
		order.setUpdatedAt(LocalDateTime.now());

		Order updatedOrder = orderRepository.save(order);
		return OrderMapper.mapToOrderDto(updatedOrder);
	}

	@Override
	public void deleteOrder(Long id) {
		if (!orderRepository.existsById(id)) {
			throw new ResourceNotFoundException("order does not exists");
		}
		orderRepository.deleteById(id);
	}
}
