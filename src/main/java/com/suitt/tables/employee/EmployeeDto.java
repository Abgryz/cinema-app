package com.suitt.tables.employee;

import com.suitt.security.user.UserDto;
import com.suitt.tables.ticketSales.TicketSalesPK;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto extends UserDto {
    private LocalDate empDate;
    private String jobTittleName;
    private List<Long> films;
}
