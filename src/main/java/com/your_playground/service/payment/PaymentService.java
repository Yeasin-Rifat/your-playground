package com.your_playground.service.payment;

import com.your_playground.entity.Payment;

import java.util.List;

public interface PaymentService {

    Double getOwnerIncome(Long ownerId);

    List<Payment> getOwnerPayments(Long ownerId);
}