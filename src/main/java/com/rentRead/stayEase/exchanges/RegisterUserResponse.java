package com.crio.stayEase.exchanges;

import com.crio.stayEase.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegisterUserResponse {

    private UserDto userDto;

    private String jwtToken;
    
}
