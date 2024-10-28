package com.crio.stayEase.exchanges;

import java.util.Set;

import com.crio.stayEase.dto.BookingDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateHotelRequest {

    private String hotelName;

    private String location;

    private String description;

    private int availableRooms;

    private Set<BookingDto> bookingDtoList;

}
