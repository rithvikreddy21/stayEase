package com.crio.stayEase.globalExceptionHandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.crio.stayEase.exceptions.BookingNotFoundException;
import com.crio.stayEase.exceptions.HotelNotFoundException;
import com.crio.stayEase.exceptions.InvalidBookingStatusException;
import com.crio.stayEase.exceptions.InvalidCredentialsException;
import com.crio.stayEase.exceptions.InvalidGuestCountException;
import com.crio.stayEase.exceptions.InvalidRoleException;
import com.crio.stayEase.exceptions.RoomsNotAvailableException;
import com.crio.stayEase.exceptions.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        String message = ex.getMessage();
        return ResponseEntity.status(404).body(message);
    }

    @ExceptionHandler(RoomsNotAvailableException.class)
    ResponseEntity<String> handleRoomsNotAvailableException(RoomsNotAvailableException ex) {
        String message = ex.getMessage();
        return ResponseEntity.status(404).body(message);
    }

    @ExceptionHandler(InvalidRoleException.class)
    ResponseEntity<String> handleInvalidRoleException(InvalidRoleException ex) {
        String message = ex.getMessage();
        return ResponseEntity.status(400).body(message);
    }

    @ExceptionHandler(InvalidGuestCountException.class)
    ResponseEntity<String> handleInvalidGuestCountException(InvalidGuestCountException ex) {
        String message = ex.getMessage();
        return ResponseEntity.status(400).body(message);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    ResponseEntity<String> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        String message = ex.getMessage();
        return ResponseEntity.status(401).body(message);
    }

    @ExceptionHandler(InvalidBookingStatusException.class)
    ResponseEntity<String> handleInvalidBookingStatusException(InvalidBookingStatusException ex) {
        String message = ex.getMessage();
        return ResponseEntity.status(400).body(message);
    }

    @ExceptionHandler(HotelNotFoundException.class)
    ResponseEntity<String> handleHotelNotFoundException(HotelNotFoundException ex) {
        String message = ex.getMessage();
        return ResponseEntity.status(404).body(message);
    }

    @ExceptionHandler(BookingNotFoundException.class)
    ResponseEntity<String> handleBookingNotFoundException(BookingNotFoundException ex) {
        String message = ex.getMessage();
        return ResponseEntity.status(404).body(message);
    }
}
