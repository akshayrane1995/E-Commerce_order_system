package com.payment.mapper;

import com.payment.dto.PaymentDto;

import com.payment.entity.Payment;

public class PaymentMapper {

	public static Payment mapToPayment(PaymentDto paymentDto){
		
		Payment payment = new Payment(
				null,
				paymentDto.orderId(),
				paymentDto.amount(),
				paymentDto.status(),
				null,
				null);
		return payment;
	}
	
	public static PaymentDto mapToPaymentDto(Payment payment){
		
		PaymentDto paymentDto = new PaymentDto(
				payment.getId(),
				payment.getOrderId(),
				payment.getAmount(),
				payment.getStatus(),
				payment.getCreatedAt(),
				payment.getUpdatedAt());
		return paymentDto;
	}
}
