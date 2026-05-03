package com.your_playground.service.payment;

import com.your_playground.entity.Payment;
import com.your_playground.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Double getOwnerIncome(Long ownerId) {

        List<Payment> payments = paymentRepository
                .findByBooking_Turf_Owner_UserIdAndStatus(ownerId, "SUCCESS");

        return payments.stream()
                .mapToDouble(Payment::getAmount)
                .sum();
    }

    @Override
    public List<Payment> getOwnerPayments(Long ownerId) {
        return paymentRepository
                .findByBooking_Turf_Owner_UserIdAndStatus(ownerId, "SUCCESS");
    }
}