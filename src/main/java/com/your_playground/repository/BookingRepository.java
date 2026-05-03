package com.your_playground.repository;

import com.your_playground.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsByTurf_TurfIdAndDateAndTimeSlot(Long turfId, LocalDate date, String timeSlot);
}