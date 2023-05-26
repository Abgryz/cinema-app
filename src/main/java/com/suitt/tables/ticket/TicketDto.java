package com.suitt.tables.ticket;

import lombok.Builder;

@Builder
public record TicketDto(
        Long id,
        double price,
        Long cinemaShow,
        Long seat
) {
}
