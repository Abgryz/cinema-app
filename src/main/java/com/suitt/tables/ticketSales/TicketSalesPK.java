package com.suitt.tables.ticketSales;

import com.suitt.tables.ticket.Ticket;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketSalesPK implements Serializable {
    @OneToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
}
