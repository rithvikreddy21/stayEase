package com.crio.stayEase.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelDto {
    
    private int id;

    private String name;

    private String location;

    private String description;

    private int availableRooms;

    private Set<BookingDto> bookings;
}
