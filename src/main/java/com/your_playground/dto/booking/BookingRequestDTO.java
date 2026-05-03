package com.your_playground.dto.booking;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class BookingRequestDTO {

    @NotNull
    private Long userId;

    @NotNull
    private Long turfId;

    @NotNull
    private LocalDate date;

    @NotNull
    private String timeSlot;

    private Double amount;
    private String paymentMethod;
    private Boolean isPaymentDone;

    // getters setters


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTurfId() {
        return turfId;
    }

    public void setTurfId(Long turfId) {
        this.turfId = turfId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Boolean getPaymentDone() {
        return isPaymentDone;
    }

    public void setPaymentDone(Boolean paymentDone) {
        isPaymentDone = paymentDone;
    }
}