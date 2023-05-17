package com.suitt.tables.employee;

import com.suitt.security.user.UserDto;
import com.suitt.tables.ticketSales.TicketSalesPK;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record EmployeeDto(
        String email,
        String fullName,
        String address,
        LocalDate birthDate,
        LocalDate empDate,
        String jobTittleName,
        List<Long> ticketSales,
        List<Long> films,
        String password,
//        String role,
        boolean active
) implements UserDto {
}
