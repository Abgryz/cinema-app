package com.suitt.tables.ticket;


import com.suitt.tables.cinemaShow.CinemaShow;
import com.suitt.tables.seat.Seat;
import com.suitt.tables.ticketSales.TicketSales;
import com.suitt.tables.ticketSales.TicketSalesPK;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ticket")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long id;

    private double price;

    @ManyToOne
    @JoinColumn(name = "cinema_show_id")
    private CinemaShow cinemaShow;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

//    @OneToOne(mappedBy = "ticketSalesPK.ticket", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private TicketSales ticketSales;
}
