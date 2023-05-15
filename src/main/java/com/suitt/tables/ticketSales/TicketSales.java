package com.suitt.tables.ticketSales;


import com.suitt.tables.client.Client;
import com.suitt.tables.employee.Employee;
import com.suitt.tables.ticket.Ticket;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "ticket_sales")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketSales {
    @EmbeddedId
//    @ManyToOne
//    @JoinColumn(name = "ticket_id")
    private TicketSalesPK ticketSalesPK;

    @ManyToOne
    @JoinColumn(name = "cl_phone_number")
    private Client client;

    private LocalDate saleDate;

    private boolean isBooking;

    @ManyToOne
    @JoinColumn(name = "emp_phone_number")
    private Employee employee;
}