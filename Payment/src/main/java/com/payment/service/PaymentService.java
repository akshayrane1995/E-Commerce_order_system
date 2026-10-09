package com.payment.service;

import java.math.BigDecimal;
import java.util.List;

import com.payment.dto.PaymentDto;

public interface PaymentService {

	PaymentDto createPayment(PaymentDto paymentDto);

	PaymentDto getPaymentById(Long id);

	List<PaymentDto> getAllPayment();

	PaymentDto updatePayment(Long id, PaymentDto paymentDto);

	void deletePaymentById(Long id);

	void processPayment(Long orderId, Long userId, BigDecimal amount);

}
