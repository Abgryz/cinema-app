package com.suitt.tables.ticketSales;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TicketSalesDto(
        Long ticket,
        String client,
        String employee,
        LocalDate saleDate,
        boolean isBooking
) {
}
