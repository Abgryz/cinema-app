package com.suitt.controllers;

import com.suitt.tables.cinemaShow.CinemaShowDto;
import com.suitt.tables.cinemaShow.CinemaShowService;
import com.suitt.tables.film.FilmService;
import com.suitt.tables.ticket.TicketService;
import com.suitt.tables.ticketSales.TicketSalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admins")
public class AdminController {
    private final TicketSalesService ticketSalesService;
    private final TicketService ticketService;
    private final FilmService filmService;
    private final CinemaShowService cinemaShowService;


    @GetMapping("/films")
    public String films(){
        return "film-adding";
    }

    @GetMapping("/shows")
    public String cinemaShows(){
        return "show-adding";
    }

    @GetMapping("/ticket-sales")
    public String ticketSales(Model model){
        model.addAttribute("ticketsSales", ticketSalesService.getAllBooking());
        return "ticket-sales";
    }

    @GetMapping("/films/{id}")
    public String updateFilm(@PathVariable Long id, Model model){
        model.addAttribute("film", filmService.getFilm(id));
        return "film-update";
    }

    @GetMapping("/shows/{id}")
    public String updateCinemaShow(@PathVariable Long id, Model model){
        CinemaShowDto cinemaShowDto = cinemaShowService.getCinemaShow(id);
        model.addAttribute("cinemaShow", cinemaShowDto);
        model.addAttribute("time", LocalTime.from(cinemaShowDto.dateAndTime()));
        model.addAttribute("date", LocalDate.from(cinemaShowDto.dateAndTime()));

        model.addAttribute("price", ticketService.getByCinemaShow(id)
                .stream()
                .findAny()
                .orElseThrow()
                .price());
        return "show-update";
    }
}
