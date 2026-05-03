package com.your_playground.exception;

public class BookingNotFound extends RuntimeException {

    public BookingNotFound(String message) {
        super(message);
    }
}
