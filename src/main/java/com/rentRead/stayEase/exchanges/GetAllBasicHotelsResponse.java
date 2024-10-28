package com.crio.stayEase.exchanges;

import java.util.List;

import com.crio.stayEase.dto.HotelBasicDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetAllBasicHotelsResponse {

    private List<HotelBasicDto> hotelBasicDtoList;

}
