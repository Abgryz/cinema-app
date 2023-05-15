package com.suitt.tables.client;


import com.suitt.tables.ticketSales.TicketSales;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "client")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @Column(name = "cl_phone_number")
    private String phoneNumber;

    @Column(name = "cl_full_name")
    private String fullName;

    private String address;

    private LocalDate birthDate;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TicketSales> ticketSales;
}