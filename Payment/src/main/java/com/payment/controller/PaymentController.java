package com.payment.controller;

import java.util.List;

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

import com.payment.dto.PaymentDto;
import com.payment.service.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	private PaymentService paymentService;
	
	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	@PostMapping
	public ResponseEntity<PaymentDto> createPayment(@Valid @RequestBody PaymentDto paymentDto){
		PaymentDto save = paymentService.createPayment(paymentDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(save);
	}
	
	@GetMapping("id/{id}")
	public ResponseEntity<PaymentDto> getPaymentById(@PathVariable Long id){
		PaymentDto paymentDto = paymentService.getPaymentById(id);
		return ResponseEntity.ok(paymentDto);
	}
	
	@GetMapping
	public ResponseEntity<List<PaymentDto>> getAllPayment(){
		List<PaymentDto> payment = paymentService.getAllPayment();
		return ResponseEntity.ok(payment);
	}
	
	@PutMapping("/id/{id}")
	public ResponseEntity<PaymentDto> updatePayment(@Valid @PathVariable Long id, @RequestBody PaymentDto paymentDto){
		PaymentDto updatedPayment = paymentService.updatePayment(id,paymentDto);
		return ResponseEntity.ok(updatedPayment);
	}
	
	@DeleteMapping("/id/{id}")
	public ResponseEntity<String> deletePaymentById(@PathVariable Long id){
		paymentService.deletePaymentById(id);
		return ResponseEntity.ok("Payment Deleted Successfull");
	}
}
