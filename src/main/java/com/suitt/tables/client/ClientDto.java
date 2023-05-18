package com.suitt.tables.client;

import com.suitt.security.user.UserDto;
import com.suitt.tables.ticketSales.TicketSalesPK;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Value
public class ClientDto extends UserDto {

}
