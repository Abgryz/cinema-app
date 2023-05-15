package com.suitt.tables.hall;

import lombok.Builder;

import java.util.List;

@Builder
public record HallDto(
        Long id,
        String type,
        int seatCount,
        List<String> cinemas,
        List<Long> seats
) {

}
