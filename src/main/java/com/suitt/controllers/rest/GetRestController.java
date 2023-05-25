package com.suitt.controllers.rest;

import com.suitt.security.user.UserService;
import com.suitt.tables.cinemaShow.CinemaShowService;
import com.suitt.tables.film.FilmService;
import com.suitt.tables.hall.HallService;
import com.suitt.tables.ticket.TicketService;
import com.suitt.tables.ticketSales.TicketSalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GetRestController {
    private final FilmService filmService;
    private final HallService hallService;
    private final CinemaShowService cinemaShowService;
    private final UserService userService;
    private final TicketSalesService ticketSalesService;
    private final TicketService ticketService;

    @GetMapping("/schedule")
    public Map<String, List<?>> getSchedule(){
        Map<String, List<?>> result = new HashMap<>();
        result.put("films", filmService.getAll());
        result.put("halls", hallService.getAll());
        result.put("cinemaShows", cinemaShowService.getNearest(1000));
        return result;
    }

    @GetMapping("/films/{id}")
    public Map<String, Object> getFilmSchedule(@PathVariable("id") Long id){
        Map<String, Object> result = new HashMap<>();
        var film = filmService.getFilm(id);
        result.put("film", film);
        result.put("cinemaShows", cinemaShowService.getNearestByFilmId(id, 1000));
        result.put("halls", hallService.getHallsFromCinemaShows(film.cinemaShows()));
        return result;
    }

    @GetMapping("/schedule/{id}")
    public List<?> tickets(@PathVariable("id") Long id){
        System.out.println(123123);
        return ticketSalesService.ticketsToMap(ticketService.getByCinemaShow(id));
    }
}
