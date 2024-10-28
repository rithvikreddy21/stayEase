package com.crio.stayEase.exchanges;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateHotelRequest {

    @NotEmpty
    private String hotelName;

    @NotEmpty
    private String location;

    @NotEmpty
    private String description;

    @Positive
    private int availableRooms;
}
