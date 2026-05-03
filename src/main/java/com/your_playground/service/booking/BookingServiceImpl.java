package com.your_playground.service.booking;

import com.your_playground.dto.booking.*;
import com.your_playground.entity.*;
import com.your_playground.enums.BookingStatus;
import com.your_playground.exception.BookingNotFound;
import com.your_playground.exception.SlotAlreadyExist;
import com.your_playground.exception.TurfNotFoundException;
import com.your_playground.exception.UserNotFoundException;
import com.your_playground.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final TurfRepository turfRepository;
    private final PaymentRepository paymentRepository;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              UserRepository userRepository,
                              TurfRepository turfRepository,
                              PaymentRepository paymentRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.turfRepository = turfRepository;
        this.paymentRepository = paymentRepository;
    }

/*  @Override
    public BookingResponseDTO createBooking(BookingRequestDTO request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Turf turf = turfRepository.findById(request.getTurfId())
                .orElseThrow(() -> new TurfNotFoundException("Turf not found"));

        // 🔥 DOUBLE BOOKING CHECK
        boolean exists = bookingRepository.existsByTurf_TurfIdAndDateAndTimeSlot(
                request.getTurfId(),
                request.getDate(),
                request.getTimeSlot()
        );

        if (exists) {
            throw new RuntimeException("This time slot is already booked");
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setTurf(turf);
        booking.setDate(request.getDate());
        booking.setTimeSlot(request.getTimeSlot());
        booking.setStatus("PENDING");

        return mapToDTO(bookingRepository.save(booking));
    }

    private BookingResponseDTO mapToDTO(Booking booking) {

        BookingResponseDTO dto = new BookingResponseDTO();
        dto.setBookingId(booking.getBookingId());
        dto.setUserId(booking.getUser().getUserId());
        dto.setTurfId(booking.getTurf().getTurfId());
        dto.setDate(booking.getDate());
        dto.setTimeSlot(booking.getTimeSlot());
        dto.setStatus(booking.getStatus());

        return dto;
    }*/

    @Override
    public BookingResponseDTO createBooking(BookingRequestDTO request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Turf turf = turfRepository.findById(request.getTurfId())
                .orElseThrow(() -> new TurfNotFoundException("Turf not found"));

        boolean exists = bookingRepository.existsByTurf_TurfIdAndDateAndTimeSlot(
                request.getTurfId(),
                request.getDate(),
                request.getTimeSlot()
        );

        if (exists) {
            throw new SlotAlreadyExist("Slot already booked");
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setTurf(turf);
        booking.setDate(request.getDate());
        booking.setTimeSlot(request.getTimeSlot());

        Payment payment = new Payment();

        // 🔥 PAYMENT LOGIC
        if (request.getPaymentDone() != null && request.getPaymentDone()) {

            if (request.getAmount() < 500) {
                throw new RuntimeException("Minimum 500 taka required");
            }

            payment.setStatus("SUCCESS");
            booking.setStatus(BookingStatus.CONFIRMED);

        } else {
            payment.setStatus("PENDING");
            booking.setStatus(BookingStatus.PENDING);
        }

        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setBooking(booking);

        Booking savedBooking = bookingRepository.save(booking);
        paymentRepository.save(payment);

        return mapToDTO(savedBooking);
    }

    @Override
    public List<BookingResponseDTO> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public BookingResponseDTO getBookingById(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFound("Booking not found"));

        return mapToDTO(booking);
    }

    @Override
    public BookingResponseDTO updateBooking(Long id, BookingRequestDTO request) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFound("Booking not found"));

        if (request.getDate() != null || request.getTimeSlot() != null || request.getTurfId() != null) {

            Long turfId = (request.getTurfId() != null) ?
                    request.getTurfId() : booking.getTurf().getTurfId();

            LocalDate date = (request.getDate() != null) ?
                    request.getDate() : booking.getDate();

            String timeSlot = (request.getTimeSlot() != null) ?
                    request.getTimeSlot() : booking.getTimeSlot();

            boolean exists = bookingRepository
                    .existsByTurf_TurfIdAndDateAndTimeSlot(turfId, date, timeSlot);

            if (exists) {
                throw new SlotAlreadyExist("This slot is already booked");
            }
        }

        // update user
        if (request.getUserId() != null) {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
            booking.setUser(user);
        }

        // update turf
        if (request.getTurfId() != null) {
            Turf turf = turfRepository.findById(request.getTurfId())
                    .orElseThrow(() -> new TurfNotFoundException("Turf not found"));
            booking.setTurf(turf);
        }

        if (request.getDate() != null) {
            booking.setDate(request.getDate());
        }

        if (request.getTimeSlot() != null) {
            booking.setTimeSlot(request.getTimeSlot());
        }

        return mapToDTO(bookingRepository.save(booking));
    }

    @Override
    public void deleteBooking(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFound("Booking not found"));

        bookingRepository.delete(booking);
    }


//    helperMethod

    private BookingResponseDTO mapToDTO(Booking booking) {

        BookingResponseDTO dto = new BookingResponseDTO();
        dto.setBookingId(booking.getBookingId());
        dto.setUserId(booking.getUser().getUserId());
        dto.setTurfId(booking.getTurf().getTurfId());
        dto.setDate(booking.getDate());
        dto.setTimeSlot(booking.getTimeSlot());
        dto.setStatus(booking.getStatus().name());

        return dto;
    }
}