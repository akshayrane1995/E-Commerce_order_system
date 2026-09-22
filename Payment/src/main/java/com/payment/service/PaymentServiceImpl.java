package com.payment.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.payment.constant.PaymentStatus;
import com.payment.dto.PaymentDto;
import com.payment.entity.Payment;
import com.payment.exception.ResourceNotFoundException;
import com.payment.mapper.PaymentMapper;
import com.payment.repository.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

	private PaymentRepository paymentRepository;

	public PaymentServiceImpl(PaymentRepository paymentRepository) {
		this.paymentRepository = paymentRepository;
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
		Payment payment = paymentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("payment does not exists"));
		return PaymentMapper.mapToPaymentDto(payment);
	}

	@Override
	public List<PaymentDto> getAllPayment() {
		List<Payment> payments = paymentRepository.findAll();
		return payments.stream().map((payment) -> PaymentMapper.mapToPaymentDto(payment)).collect(Collectors.toList());
	}

	@Override
	public PaymentDto updatePayment(Long id, PaymentDto paymentDto) {
		Payment payment = paymentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("payment does not exists"));

		payment.setAmount(paymentDto.amount());
		payment.setStatus(paymentDto.status());
		payment.setUpdatedAt(LocalDateTime.now());

		Payment updatedPayment = paymentRepository.save(payment);
		return PaymentMapper.mapToPaymentDto(updatedPayment);
	}

	@Override
	public void deletePaymentById(Long id) {
		if(!paymentRepository.existsById(id)) {
			throw new ResourceNotFoundException("payment does not exists");
		}
			paymentRepository.deleteById(id);
	}

}
