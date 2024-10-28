package com.crio.stayEase.exchanges;

import java.time.LocalDate;

import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookRoomRequest {

    @Positive
    private int guests;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;
}
