package com.amazon.dto;

import com.amazon.constant.OrderHistoryStatus;
import com.amazon.entity.Account;
import com.amazon.entity.Address;
import com.amazon.entity.Payment;

public class OrderHistoryDto {

    private int id;
    private Account account;
    private double totalAmount;
    private Payment payment;
    private Address address;
    private OrderHistoryStatus status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public OrderHistoryStatus getStatus() {
        return status;
    }

    public void setStatus(OrderHistoryStatus status) {
        this.status = status;
    }
}
