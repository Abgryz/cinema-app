package com.suitt.tables.employee;

import com.suitt.tables.jobTittle.JobTittle;
import com.suitt.tables.ticketSales.TicketSalesPK;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record EmployeeDto(
        String phoneNumber,
        String fullName,
        String address,
        LocalDate birthDate,
        LocalDate empDate,
        String jobTittleName,
        List<TicketSalesPK> ticketSales,
        List<Long> films
) {
}
