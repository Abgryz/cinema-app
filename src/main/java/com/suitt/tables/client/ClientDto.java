package com.suitt.tables.client;

import com.suitt.tables.ticketSales.TicketSalesPK;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record ClientDto(
        String phoneNumber,
        String fullName,
        String address,
        LocalDate birthDate,
        List<TicketSalesPK> ticketSales
) {
}
