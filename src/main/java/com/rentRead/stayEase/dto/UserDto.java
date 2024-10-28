package com.crio.stayEase.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UserDto {
    
    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String role;

    @JsonIgnore
    private Set<BookingDto> bookings;
}
