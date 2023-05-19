package com.suitt.controllers.rest;

import com.suitt.security.user.UserService;
import com.suitt.tables.ticket.Ticket;
import com.suitt.tables.ticket.TicketRepository;
import com.suitt.tables.ticketSales.TicketSalesService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserRestController {
    private final TicketSalesService ticketSalesService;
    private final TicketRepository ticketRepository;

    @PostMapping("/schedule")
    @Transactional
    public void ticketBooking(@RequestParam("ticketId") Long ticketId){
        ticketSalesService.create(
                ticketRepository.findById(ticketId).orElseThrow(),
                UserService.authentication().getName(),
                true
        );
    }
//
//    @GetMapping("/profile")
//    public List<Object> profileTickets(){
//        return ticketRepository.findTicketBookingDataByClient(UserService.authentication().getName());
//    }
}
