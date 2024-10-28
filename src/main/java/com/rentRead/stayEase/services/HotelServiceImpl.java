package com.crio.stayEase.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.crio.stayEase.dto.BookingDto;
import com.crio.stayEase.dto.HotelBasicDto;
import com.crio.stayEase.dto.HotelDto;
import com.crio.stayEase.exchanges.BookRoomRequest;
import com.crio.stayEase.exchanges.CreateHotelRequest;
import com.crio.stayEase.exchanges.GetAllBasicHotelsResponse;
import com.crio.stayEase.exchanges.UpdateHotelRequest;
import com.crio.stayEase.mapper.Mapper;
import com.crio.stayEase.repositoryServices.HotelRepositoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
 
    private final HotelRepositoryService hotelRepositoryService;

    private BookingService bookingService;

    @Autowired
    public void setBookingService(@Lazy BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public HotelDto createHotel(CreateHotelRequest createHotelRequest) {
        String hotelName = createHotelRequest.getHotelName();
        String location = createHotelRequest.getLocation();
        String description = createHotelRequest.getDescription();
        int availableRooms = createHotelRequest.getAvailableRooms();

        HotelDto hotelDto = hotelRepositoryService.createHotel(hotelName, location, description, availableRooms);
        return hotelDto;
    }

    @Override
    public BookingDto createBooking(int hotelId, BookRoomRequest bookRoomsRequest, UserDetails userDetails) {
        return bookingService.bookRoom(hotelId, bookRoomsRequest, userDetails);
    }

    @Override
    public HotelBasicDto findHotelByIdForCustomers(int hotelId) {
        HotelDto hotelDto = hotelRepositoryService.findHotelById(hotelId);
        return Mapper.mapToHotelBasicDto(hotelDto);
    }

    @Override
    public GetAllBasicHotelsResponse findAllHotelsForCustomers() {
        List<HotelDto> hotelDtoList = hotelRepositoryService.findAllHotels();
        List<HotelBasicDto> hotelBasicDtoList = Mapper.mapToHotelBasicDtoList(hotelDtoList);
        return new GetAllBasicHotelsResponse(hotelBasicDtoList);
    }

    @Override
    public HotelDto findHotelByIdForManager(int hotelId) {
        HotelDto hotelDto = hotelRepositoryService.findHotelById(hotelId);
        return hotelDto;
    }

    @Override
    public HotelDto updateHotel(int hotelId, UpdateHotelRequest updateHotelRequest) {
        String hotelName = updateHotelRequest.getHotelName();
        String location = updateHotelRequest.getLocation();
        String description = updateHotelRequest.getDescription();
        int availableRooms = updateHotelRequest.getAvailableRooms();
        Set<BookingDto> bookingDtoList = updateHotelRequest.getBookingDtoList();

        HotelDto hotelDto = hotelRepositoryService.findHotelById(hotelId);

        if(hotelName != null) hotelDto.setName(hotelName);
        if(location != null) hotelDto.setLocation(location);
        if(description != null) hotelDto.setDescription(description);
        if(availableRooms > 0) hotelDto.setAvailableRooms(availableRooms);
        if(bookingDtoList != null) hotelDto.setBookings(bookingDtoList);
    
        return hotelRepositoryService.saveHotel(hotelDto);
    }

    @Override
    public HotelDto saveHotel(HotelDto hotelDto) {
        return hotelRepositoryService.saveHotel(hotelDto);
    }

    @Override
    public String deleteHotel(int hotelId) {
        String response = "Successfully deleted hotel with ID: " + String.valueOf(hotelId);
        hotelRepositoryService.deleteHotel(hotelId);
        return response;
    }

}
