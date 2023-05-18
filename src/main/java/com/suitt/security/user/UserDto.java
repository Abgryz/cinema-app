package com.suitt.security.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@SuperBuilder
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
