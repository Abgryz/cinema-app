package com.suitt.tables.client;

import com.suitt.security.user.UserDto;
import com.suitt.tables.ticketSales.TicketSalesPK;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record ClientDto(
        String email,
        String fullName,
        String address,
        LocalDate birthDate,
        List<Long> ticketSales,
        String password,
//        String role,
        boolean active
) implements UserDto {
}
