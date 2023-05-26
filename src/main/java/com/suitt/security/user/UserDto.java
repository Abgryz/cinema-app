package com.suitt.security.user;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    protected String email;
    protected String fullName;
    protected String address;
    protected LocalDate birthDate;
    protected String password;
    protected String role;
    protected boolean active;
    protected List<Long> ticketSales;
}
