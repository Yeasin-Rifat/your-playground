package com.your_playground.service.booking;

import com.your_playground.dto.booking.*;

import java.util.List;

public interface BookingService {

    BookingResponseDTO createBooking(BookingRequestDTO request);

    List<BookingResponseDTO> getAllBookings();

    BookingResponseDTO getBookingById(Long id);

    BookingResponseDTO updateBooking(Long id, BookingRequestDTO request);

    void deleteBooking(Long id);
}