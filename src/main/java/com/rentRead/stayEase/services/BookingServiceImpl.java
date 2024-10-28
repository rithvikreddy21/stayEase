package com.crio.stayEase.services;

import java.time.LocalDate;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crio.stayEase.dto.BookingDto;
import com.crio.stayEase.dto.HotelDto;
import com.crio.stayEase.entities.enums.BookingStatus;
import com.crio.stayEase.exceptions.InvalidBookingStatusException;
import com.crio.stayEase.exceptions.InvalidGuestCountException;
import com.crio.stayEase.exceptions.RoomsNotAvailableException;
import com.crio.stayEase.exchanges.BookRoomRequest;
import com.crio.stayEase.repositoryServices.BookingRepositoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepositoryService bookingRepositoryService;

    private final HotelService hotelService;

    @Override
    @Transactional
    public BookingDto bookRoom(int hotelId, BookRoomRequest bookRoomsRequest, UserDetails userDetails) {
        HotelDto hotelDto = hotelService.findHotelByIdForManager(hotelId);
        int availableRooms = hotelDto.getAvailableRooms();
        int guests = bookRoomsRequest.getGuests();
        String userEmail = userDetails.getUsername();
            
        checkRoomAvailability(availableRooms);
        validateRoomCapacity(guests);

        hotelDto.setAvailableRooms(availableRooms - 1);
        hotelService.saveHotel(hotelDto);
        BookingDto bookingDto = bookingRepositoryService.createBooking(hotelId, userEmail, guests, bookRoomsRequest.getCheckInDate(), bookRoomsRequest.getCheckOutDate());
        return bookingDto;
    }

    @Override
    public BookingDto checkIn(int bookingId) {
        BookingDto bookingDto = bookingRepositoryService.findBookingById(bookingId);
        BookingStatus bookingStatus = bookingDto.getBookingStatus();
        LocalDate checkInDate = bookingDto.getCheckInDate();
        LocalDate checkOutDate = bookingDto.getCheckOutDate();
        LocalDate currentDate = LocalDate.now();

        if(!bookingStatus.equals(BookingStatus.BOOKED))
            throw new InvalidBookingStatusException("Booking status must be 'BOOKED' to check in"); 

        if(currentDate.isBefore(checkInDate) || currentDate.isAfter(checkOutDate))
            throw new InvalidBookingStatusException("Current date is outside the valid check-in/check-out range");
            
        bookingDto.setBookingStatus(BookingStatus.CHECKED_IN);

        return bookingRepositoryService.updateBooking(bookingDto);
    }

    @Override
    @Transactional
    public BookingDto checkOut(int bookingId) {
        BookingDto bookingDto = bookingRepositoryService.findBookingById(bookingId);
        BookingStatus bookingStatus = bookingDto.getBookingStatus();
    
        if(!bookingStatus.equals(BookingStatus.CHECKED_IN))
            throw new InvalidBookingStatusException("Booking status must be 'CHECKED_IN' to check out"); 

        HotelDto hotelDto = hotelService.findHotelByIdForManager(bookingDto.getBookedHotel().getId());
        hotelDto.setAvailableRooms(hotelDto.getAvailableRooms() + 1);
        hotelService.saveHotel(hotelDto);

        bookingDto.setBookingStatus(BookingStatus.CHECKED_OUT);
        return bookingRepositoryService.updateBooking(bookingDto);
    }

    @Override
    @Transactional
    public String cancelBooking(int bookingId) {
        String response = "Successfully cancelled the booking with id: " + bookingId;
        BookingDto bookingDto = bookingRepositoryService.findBookingById(bookingId);
        BookingStatus bookingStatus = bookingDto.getBookingStatus();
        LocalDate checkInDate = bookingDto.getCheckInDate();
        LocalDate currentDate = LocalDate.now();

        if(!(bookingStatus.equals(BookingStatus.BOOKED) && currentDate.isBefore(checkInDate)))
            throw new InvalidBookingStatusException("Cannot cancel the booking. Booking status must be 'BOOKED' and cancellation date should be before the CHECK_IN date in order to cancel the booking");

        HotelDto hotelDto = hotelService.findHotelByIdForManager(bookingDto.getBookedHotel().getId());
        hotelDto.setAvailableRooms(hotelDto.getAvailableRooms() + 1);
        hotelService.saveHotel(hotelDto);

        bookingRepositoryService.cancelBooking(bookingDto);
        return response;
    }

    private void checkRoomAvailability(int availableRooms) {
        if(availableRooms == 0)
            throw new RoomsNotAvailableException("Requested rooms are not available for booking");
    }
    
    private void validateRoomCapacity(int guests) {
        final int maxRoomCapacity = 2;
        final int minRoomCapacity = 1;

        if(guests < minRoomCapacity)
            throw new InvalidGuestCountException("Please provide at least " + minRoomCapacity + " guests to continue booking");

        if(guests > maxRoomCapacity)
            throw new InvalidGuestCountException("Provided number of guests exceeds the room capacity");
    }
}
