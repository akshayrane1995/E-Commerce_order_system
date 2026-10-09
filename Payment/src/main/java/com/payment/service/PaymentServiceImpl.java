package com.payment.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.payment.constant.PaymentStatus;
import com.payment.dto.PaymentDto;
import com.payment.entity.Payment;
import com.payment.event.PaymentResultEvent;
import com.payment.exception.ResourceNotFoundException;
import com.payment.kafka.PaymentEventProducer;
import com.payment.mapper.PaymentMapper;
import com.payment.repository.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

	private PaymentRepository paymentRepository;
	private final PaymentEventProducer paymentEventProducer;

	public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentEventProducer paymentEventProducer) {
		this.paymentRepository = paymentRepository;
		this.paymentEventProducer = paymentEventProducer;
	}

	@Override
	public PaymentDto createPayment(PaymentDto paymentDto) {
		Payment payment = PaymentMapper.mapToPayment(paymentDto);
		LocalDateTime now = LocalDateTime.now();
		payment.setCreatedAt(now);
		payment.setUpdatedAt(now);
		payment.setStatus(PaymentStatus.PENDING);
		Payment save = paymentRepository.save(payment);
		return PaymentMapper.mapToPaymentDto(save);
	}

	@Override
	public PaymentDto getPaymentById(Long id) {
		Payment payment = paymentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("payment does not exists"));
		return PaymentMapper.mapToPaymentDto(payment);
	}

	@Override
	public List<PaymentDto> getAllPayment() {
		List<Payment> payments = paymentRepository.findAll();
		return payments.stream().map((payment) -> PaymentMapper.mapToPaymentDto(payment)).collect(Collectors.toList());
	}

	@Override
	public PaymentDto updatePayment(Long id, PaymentDto paymentDto) {
		Payment payment = paymentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("payment does not exists"));

		payment.setAmount(paymentDto.amount());
		payment.setStatus(paymentDto.status());
		payment.setUpdatedAt(LocalDateTime.now());

		Payment updatedPayment = paymentRepository.save(payment);
		return PaymentMapper.mapToPaymentDto(updatedPayment);
	}

	@Override
	public void deletePaymentById(Long id) {
		if (!paymentRepository.existsById(id)) {
			throw new ResourceNotFoundException("payment does not exists");
		}
		paymentRepository.deleteById(id);
	}

	@Override
	public void processPayment(Long orderId, Long userId, BigDecimal amount) {

		if (paymentRepository.existsByOrderId(orderId)) {
			System.out.println("Payment already exists for order: " + orderId);
			return;
		}

		Payment payment = new Payment();
		payment.setOrderId(orderId);
		payment.setAmount(amount);

		LocalDateTime now = LocalDateTime.now();
		payment.setCreatedAt(now);
		payment.setUpdatedAt(now);
		payment.setStatus(PaymentStatus.PENDING);

		Payment savePayment = paymentRepository.save(payment);


		// Mock payment processing
		boolean paymentSuccessful = amount != null && amount.compareTo(BigDecimal.ZERO) > 0;

		//testing for payment failure mock
		//boolean paymentSuccessful = orderId % 2 != 0;
		
		if (paymentSuccessful) {
			savePayment.setStatus(PaymentStatus.SUCCESS);
			savePayment.setUpdatedAt(LocalDateTime.now());
			paymentRepository.save(savePayment);

			PaymentResultEvent event = new PaymentResultEvent(
					orderId,
					userId,
					amount,
					savePayment.getStatus().name(),
					"Payment processed successfully"
			);

			paymentEventProducer.publishPaymentResult(event);
			System.out.println("Payment Successful for order: " + orderId);

		} else {
			savePayment.setStatus(PaymentStatus.FAILED);
			savePayment.setUpdatedAt(LocalDateTime.now());
			paymentRepository.save(savePayment);

			PaymentResultEvent event = new PaymentResultEvent(
					orderId,
					userId,
					amount,
					savePayment.getStatus().name(),
					"Payment failed"
			);

			paymentEventProducer.publishPaymentResult(event);
			System.out.println("Payment Failed for order: " + orderId);
		}
	}

}
