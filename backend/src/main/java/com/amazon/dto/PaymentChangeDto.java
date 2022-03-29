package com.amazon.dto;

import com.amazon.constant.PaymentType;

public class PaymentChangeDto {

    private PaymentType paymentType;
    private String number;
    private Boolean enabled;

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public PaymentChangeDto() {}

    public PaymentChangeDto(PaymentType paymentType, String number, Boolean enabled) {
        this.paymentType = paymentType;
        this.number = number;
        this.enabled = enabled;
    }
}
