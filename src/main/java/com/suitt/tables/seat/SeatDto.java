package com.suitt.tables.seat;

import lombok.Builder;

import java.util.List;

@Builder
public record SeatDto(
        Long id,
        Long hall,
        int row,
        int seatNumber,
        double priceCoefficient,
        List<Long> tickets
) {
}
