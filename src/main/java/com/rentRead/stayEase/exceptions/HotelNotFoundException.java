package com.crio.stayEase.exceptions;

public class HotelNotFoundException extends RuntimeException {
    
    public HotelNotFoundException(String message) {
        super(message);
    }
    
}
