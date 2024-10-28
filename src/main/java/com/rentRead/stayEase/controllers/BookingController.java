package com.crio.stayEase.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crio.stayEase.dto.BookingDto;
import com.crio.stayEase.services.BookingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(BookingController.BOOKING_API_ENDPOINT)
@RequiredArgsConstructor
public class BookingController {

    public static final String BOOKING_API_ENDPOINT = "/bookings";

    private final BookingService bookingService;

    @PreAuthorize("hasRole('HOTEL_MANAGER')")
    @PostMapping("/{bookingId}/check-in")
    public ResponseEntity<BookingDto> checkIn(@PathVariable(value = "bookingId") int bookingId) {
        return ResponseEntity.ok(bookingService.checkIn(bookingId));
    }

    @PreAuthorize("hasRole('HOTEL_MANAGER')")
    @PostMapping("/{bookingId}/check-out")
    public ResponseEntity<BookingDto> checkOut(@PathVariable(value = "bookingId") int bookingId) {
        return ResponseEntity.ok(bookingService.checkOut(bookingId));
    }

    @PreAuthorize("hasRole('HOTEL_MANAGER')")
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<String> cancelBooking(@PathVariable(value = "bookingId") int bookingId) {
        return ResponseEntity.ok(bookingService.cancelBooking(bookingId));
    }

}
