package com.amazon.service;

import com.amazon.constant.PaymentType;
import com.amazon.dto.PaymentChangeDto;
import com.amazon.entity.Account;
import com.amazon.entity.Payment;
import com.amazon.entity.Role;
import com.amazon.repository.PaymentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("A test case for class PaymentService.")
@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;
    private PaymentService paymentService;
    private Account account;
    private PaymentChangeDto paymentChangeDto;
    private Payment payment;


    @BeforeEach
    void setup() {
        paymentService = new PaymentService(paymentRepository);
        account = new Account("AAA", "BBB", "aaabbb@gmail.com", "aaabbb123", new Role(1, "CUSTOMER"));
        paymentChangeDto = new PaymentChangeDto(PaymentType.CREDIT_CARD, "123456", true);
        payment = new Payment(account, paymentChangeDto.getPaymentType(), paymentChangeDto.getNumber(), true);
    }

    @AfterEach
    void tearDown() {
    }

    @DisplayName("Test create method.")
    @Test
    public void testCreate() {
        Payment payment = new Payment(account, PaymentType.CREDIT_CARD, "123456", true);
        when(paymentRepository.save(payment)).thenReturn(payment);
        Payment actual = paymentService.create(paymentChangeDto, account);
        assertThat(actual.getNumber()).isEqualTo(payment.getNumber());
    }

    @DisplayName("Test update method.")
    @Test
    public void testUpdate() {
        int paymentId = 1;
        payment.setId(paymentId);
        when(paymentRepository.findByAccountAndId(account, paymentId)).thenReturn(payment);
        when(paymentService.checkPayment(paymentId, account)).thenReturn(payment);
        paymentService.update(paymentId, paymentChangeDto, account);
        verify(paymentRepository).save(payment);
    }

    @DisplayName("Test getAll method.")
    @Test
    public void testGetAll() {
        paymentService.getAll(account);
        verify(paymentRepository).findAllByAccount(account);
    }

    @DisplayName("Test delete method.")
    @Test
    public void testDelete() {
        int paymentId = 1;
        payment.setId(paymentId);
        when(paymentRepository.findByAccountAndId(account, paymentId)).thenReturn(payment);
        when(paymentService.checkPayment(paymentId, account)).thenReturn(payment);
        paymentService.delete(paymentId, account);
        verify(paymentRepository).delete(payment);
    }
}
