package com.amazon.controller;

import com.amazon.dto.PaymentChangeDto;
import com.amazon.dto.PaymentDto;
import com.amazon.entity.Account;
import com.amazon.mapper.PaymentMapper;
import com.amazon.service.AccountService;
import com.amazon.service.PaymentService;
import com.amazon.util.Util;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer/payment")
public class CustomerPaymentController {

    private final AccountService accountService;
    private final PaymentService paymentService;

    public CustomerPaymentController(AccountService accountService, PaymentService paymentService) {
        this.accountService = accountService;
        this.paymentService = paymentService;
    }

    @PostMapping
    public PaymentDto createPaymentMethod(@RequestBody PaymentChangeDto paymentChangeDto) {
        int accountId = Util.getCurrentUser().getAccountId();
        Account account = accountService.findById(accountId);
        return PaymentMapper.INSTANCE.map(paymentService.create(paymentChangeDto, account));
    }

    @PutMapping("/{paymentId}")
    public PaymentDto updatePaymentMethod(@PathVariable int paymentId,
                                          @RequestBody PaymentChangeDto paymentChangeDto) {
        int accountId = Util.getCurrentUser().getAccountId();
        Account account = accountService.findById(accountId);
        return PaymentMapper.INSTANCE.map(paymentService.update(paymentId, paymentChangeDto, account));
    }

    @GetMapping
    public List<PaymentDto> getAllPaymentMethods() {
        int accountId = Util.getCurrentUser().getAccountId();
        Account account = accountService.findById(accountId);
        return PaymentMapper.INSTANCE.map(paymentService.getAll(account));
    }

    @DeleteMapping("/{paymentId}")
    public void deletePaymentMethod(@PathVariable int paymentId) {
        int accountId = Util.getCurrentUser().getAccountId();
        Account account = accountService.findById(accountId);
        paymentService.delete(paymentId, account);
    }
}
