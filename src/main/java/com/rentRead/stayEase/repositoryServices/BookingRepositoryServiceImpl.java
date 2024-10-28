package com.crio.stayEase.repositoryServices;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.crio.stayEase.dto.BookingDto;
import com.crio.stayEase.entities.Booking;
import com.crio.stayEase.entities.Hotel;
import com.crio.stayEase.entities.User;
import com.crio.stayEase.entities.enums.BookingStatus;
import com.crio.stayEase.exceptions.BookingNotFoundException;
import com.crio.stayEase.mapper.Mapper;
import com.crio.stayEase.repositories.BookingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingRepositoryServiceImpl implements BookingRepositoryService {

    private final UserRepositoryService userRepositoryService;

    private final HotelRepositoryService hotelRepositoryService; 

    private final BookingRepository bookingRepository;

    @Override
    public BookingDto createBooking(int hotelId, String userEmail, int guests, LocalDate checkInDate, LocalDate checkOutDate) {
        User user = Mapper.mapToUser(userRepositoryService.findUserByEmail(userEmail));
        Hotel hotel = Mapper.mapToHotel(hotelRepositoryService.findHotelById(hotelId));
        
        Booking booking = Booking.builder()
                          .user(user)
                          .bookedHotel(hotel)
                          .guests(guests)
                          .checkInDate(checkInDate)
                          .checkOutDate(checkOutDate)
                          .bookingStatus(BookingStatus.BOOKED)
                          .bookingDate(LocalDate.now())
                          .build();
        
        Booking savedBooking = bookingRepository.save(booking);
        return Mapper.mapToBookingDto(savedBooking);
    }

    @Override
    public BookingDto updateBooking(BookingDto bookingDto) {
        Booking updatedBooking = bookingRepository.save(Mapper.mapToBooking(bookingDto));
        return Mapper.mapToBookingDto(updatedBooking);
    }

    @Override
    public BookingDto findBookingById(int bookingId) {
        String message = "Could not find booking with bookingId: " + bookingId;
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException(message));
        return Mapper.mapToBookingDto(booking);
        
    }

    @Override
    public void cancelBooking(BookingDto bookingDto) {
        bookingRepository.delete(Mapper.mapToBooking(bookingDto));
    }
    
}
