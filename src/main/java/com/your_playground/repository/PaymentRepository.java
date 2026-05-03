package com.your_playground.repository;

import com.your_playground.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByBooking_Turf_Owner_UserIdAndStatus(Long ownerId, String status);
}