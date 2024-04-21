package com.suitt.tables.hall;

import lombok.Builder;

import java.util.List;

@Builder
public record HallDto(
        Long id,
        List<Long> cinemas,
        List<Long> seats
) {

}
