package com.crio.stayEase.services;

import org.springframework.security.core.userdetails.UserDetails;

import com.crio.stayEase.dto.BookingDto;
import com.crio.stayEase.exchanges.BookRoomRequest;

public interface BookingService {
    
    BookingDto bookRoom(int hotelId, BookRoomRequest bookRoomsRequest, UserDetails userDetails);

    BookingDto checkIn(int bookingId);

    BookingDto checkOut(int bookingId);

    String cancelBooking(int bookingId);
}
