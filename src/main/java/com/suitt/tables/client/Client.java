package com.suitt.tables.client;


import com.suitt.security.user.UserEntity;
import com.suitt.tables.ticketSales.TicketSales;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "client")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client extends UserEntity {
    @Id
    @Column(name = "cl_email")
    private String email;

    @Column(name = "cl_full_name")
    private String fullName;

    private String address;

    private LocalDate birthDate;

    private String password;

    private boolean active;

//    private final String role = Role.USER.name();

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TicketSales> ticketSales;
}