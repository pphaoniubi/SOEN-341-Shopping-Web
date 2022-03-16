package com.amazon.repository;

import com.amazon.entity.Account;
import com.amazon.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Payment findByAccountAndId(Account account, int paymentId);
    List<Payment> findAllByAccount(Account account);
}
