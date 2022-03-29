package com.amazon.service;

import com.amazon.dto.PaymentChangeDto;
import com.amazon.entity.Account;
import com.amazon.entity.Payment;
import com.amazon.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment create(PaymentChangeDto paymentChangeDto, Account account) {
        Payment payment = new Payment(account, paymentChangeDto.getPaymentType(), paymentChangeDto.getNumber(), true);
        return null; //paymentRepository.save(payment);
    }

    public Payment update(int paymentId, PaymentChangeDto paymentChangeDto, Account account) {
        Payment payment = checkPayment(paymentId, account);
        if (Objects.nonNull(paymentChangeDto.getPaymentType())) payment.setPaymentType(paymentChangeDto.getPaymentType());
        if (Objects.nonNull(paymentChangeDto.getNumber())) payment.setNumber(paymentChangeDto.getNumber());
        if (Objects.nonNull(paymentChangeDto.getEnabled())) payment.setEnabled(paymentChangeDto.getEnabled());
        return paymentRepository.save(payment);
    }

    public List<Payment> getAll(Account account) {
        return paymentRepository.findAllByAccount(account);
    }

    public void delete(int paymentId, Account account) {
        paymentRepository.delete(checkPayment(paymentId, account));
    }

    public Payment checkPayment(int paymentId, Account account) {
        Payment payment = paymentRepository.findByAccountAndId(account, paymentId);
        if (Objects.isNull(payment)) {
            throw new IllegalArgumentException("There's no such payment method for this customer.");
        }
        return payment;
    }
}
