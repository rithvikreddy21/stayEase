package com.crio.stayEase.exceptions;

public class InvalidBookingStatusException extends RuntimeException {

    public InvalidBookingStatusException(String message) {
        super(message);
    }
    
}
