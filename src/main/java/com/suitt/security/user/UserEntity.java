package com.suitt.security.user;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserEntity {
    @Id
    protected String email;

    protected String fullName;

    protected String address;

    protected LocalDate birthDate;

    protected String password;

    protected boolean active;
}
