package com.suitt.controllers;

import com.suitt.tables.genre.GenreService;
import com.suitt.tables.ticketSales.TicketSalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admins")
public class AdminController {
    private final TicketSalesService ticketSalesService;

    @GetMapping("/films")
    public String films(Model model){
        return "film-adding";
    }

    @GetMapping("/shows")
    public String cinemaShows(Model model){
        return "show-adding";
    }

    @GetMapping("/ticket-sales")
    public String ticketSales(Model model){
        model.addAttribute("ticketsSales", ticketSalesService.getAllBooking());
        return "ticket-sales";
    }

}
