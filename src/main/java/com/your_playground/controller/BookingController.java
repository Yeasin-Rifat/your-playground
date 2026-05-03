package com.your_playground.controller;

import com.your_playground.dto.booking.*;
import com.your_playground.errorhandler.ApiResponse;
import com.your_playground.service.booking.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // CREATE
    @PostMapping("/create")
    public ApiResponse<BookingResponseDTO> create(@RequestBody BookingRequestDTO request) {
        return new ApiResponse<>("Booking created successfully",
                bookingService.createBooking(request));
    }

    // GET ALL
    @GetMapping
    public ApiResponse<List<BookingResponseDTO>> getAll() {
        return new ApiResponse<>("All bookings",
                bookingService.getAllBookings());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ApiResponse<BookingResponseDTO> getById(@PathVariable Long id) {
        return new ApiResponse<>("Booking found",
                bookingService.getBookingById(id));
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ApiResponse<BookingResponseDTO> update(@PathVariable Long id,
                                                  @RequestBody BookingRequestDTO request) {

        return new ApiResponse<>("Booking updated",
                bookingService.updateBooking(id, request));
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {

        bookingService.deleteBooking(id);

        return new ApiResponse<>("Booking deleted", null);
    }
}