package com.suitt.tables.client;


import com.suitt.security.user.UserEntity;
import com.suitt.tables.ticketSales.TicketSales;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "client")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Client extends UserEntity {

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<TicketSales> ticketSales;
}