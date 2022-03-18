package com.amazon.dto;

import com.amazon.constant.OrderHistoryStatus;

public class OrderHistoryDto {

    private int id;
    private AccountDto account;
    private double totalAmount;
    private PaymentDto payment;
    private AddressDto address;
    private OrderHistoryStatus status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AccountDto getAccount() {
        return account;
    }

    public void setAccount(AccountDto account) {
        this.account = account;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public PaymentDto getPayment() {
        return payment;
    }

    public void setPayment(PaymentDto payment) {
        this.payment = payment;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public OrderHistoryStatus getStatus() {
        return status;
    }

    public void setStatus(OrderHistoryStatus status) {
        this.status = status;
    }
}
