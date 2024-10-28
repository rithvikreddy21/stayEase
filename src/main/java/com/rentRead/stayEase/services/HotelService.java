package com.crio.stayEase.services;

import org.springframework.security.core.userdetails.UserDetails;

import com.crio.stayEase.dto.BookingDto;
import com.crio.stayEase.dto.HotelBasicDto;
import com.crio.stayEase.dto.HotelDto;
import com.crio.stayEase.exchanges.BookRoomRequest;
import com.crio.stayEase.exchanges.CreateHotelRequest;
import com.crio.stayEase.exchanges.GetAllBasicHotelsResponse;
import com.crio.stayEase.exchanges.UpdateHotelRequest;

public interface HotelService {
    
    HotelDto createHotel(CreateHotelRequest createHotelRequest);

    BookingDto createBooking(int hotelId, BookRoomRequest bookRoomsRequest, UserDetails userDetails);
    
    HotelBasicDto findHotelByIdForCustomers(int hotelId);

    GetAllBasicHotelsResponse findAllHotelsForCustomers();

    HotelDto findHotelByIdForManager(int hotelId);

    HotelDto updateHotel(int hotelId, UpdateHotelRequest updateHotelRequest);

    HotelDto saveHotel(HotelDto hotelDto);

    String deleteHotel(int hotelId);
}
