package com.crio.stayEase.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crio.stayEase.dto.BookingDto;
import com.crio.stayEase.dto.HotelBasicDto;
import com.crio.stayEase.dto.HotelDto;
import com.crio.stayEase.exchanges.BookRoomRequest;
import com.crio.stayEase.exchanges.CreateHotelRequest;
import com.crio.stayEase.exchanges.GetAllBasicHotelsResponse;
import com.crio.stayEase.exchanges.UpdateHotelRequest;
import com.crio.stayEase.services.HotelService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(HotelController.HOTEL_API_ENDPOINT)
@RequiredArgsConstructor
public class HotelController {
    
    public static final String HOTEL_API_ENDPOINT = "/hotels";

    private final HotelService hotelService; 

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<HotelDto> createHotel(@Valid @RequestBody CreateHotelRequest createHotelRequest) {
        return ResponseEntity.ok(hotelService.createHotel(createHotelRequest));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/{hotelId}/book") 
    public ResponseEntity<BookingDto> createBooking(@PathVariable(value = "hotelId") int hotelId, @Valid @RequestBody BookRoomRequest bookRoomRequest, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(hotelService.createBooking(hotelId, bookRoomRequest, userDetails));
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelBasicDto> findHotelByIdForCustomers(@PathVariable( value = "hotelId") int hotelId) {
        return ResponseEntity.ok(hotelService.findHotelByIdForCustomers(hotelId));
    }

    @GetMapping
    public ResponseEntity<GetAllBasicHotelsResponse> findAllHotelsForCustomers() {
        return ResponseEntity.ok(hotelService.findAllHotelsForCustomers());
    }

    @PreAuthorize("hasRole('HOTEL_MANAGER')")
    @GetMapping("/manager/{hotelId}")
    public ResponseEntity<HotelDto> findHotelByIdForManager(@PathVariable( value = "hotelId") int hotelId) {
        return ResponseEntity.ok(hotelService.findHotelByIdForManager(hotelId));
    }

    @PreAuthorize("hasRole('HOTEL_MANAGER')")
    @PatchMapping("/{hotelId}")
    public ResponseEntity<HotelDto> updateHotel(@PathVariable(value = "hotelId") int hotelId, @RequestBody UpdateHotelRequest updateHotelRequest) {
        return ResponseEntity.ok(hotelService.updateHotel(hotelId, updateHotelRequest));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{hotelId}") 
    public ResponseEntity<String> deleteHotel(@PathVariable(value = "hotelId") int hotelId) {
        return ResponseEntity.ok(hotelService.deleteHotel(hotelId));
    }

}
