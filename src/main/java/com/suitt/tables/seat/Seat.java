package com.suitt.tables.seat;


import com.suitt.tables.hall.Hall;
import com.suitt.tables.ticket.Ticket;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "seat")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Seat {
    @Id
    @GeneratedValue
    @Column(name = "seat_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hall_id")
    private Hall hall;

    private int seatRow;

    @Column(name = "seat_num")
    private int seatNumber;

    @Column(name = "price_coef")
    private double priceCoefficient;

    @OneToMany(mappedBy = "seat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Ticket> tickets;
}
