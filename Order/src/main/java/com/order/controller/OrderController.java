package com.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.dto.OrderDto;
import com.order.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrderController {

	private OrderService orderService;
	
	public OrderController(OrderService orderService){
		this.orderService = orderService;
	}
	
	@PostMapping("/create")
	public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderDto orderDto){
		OrderDto save = orderService.createOrder(orderDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(save);
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id){
		OrderDto orderDto = orderService.getOrderById(id);
		return ResponseEntity.ok(orderDto);
	}
	
	@PutMapping("/{id}/update")
	public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderDto orderDto){
		OrderDto updatedOrder = orderService.updateOrder(id,orderDto);
		return ResponseEntity.ok(updatedOrder);
	}
	
	@DeleteMapping("/{id}/remove")
	public ResponseEntity<String> deleteOrder(@PathVariable Long id){
		orderService.deleteOrder(id);
		return ResponseEntity.ok("order is deleted sucessfully");
	}
}
