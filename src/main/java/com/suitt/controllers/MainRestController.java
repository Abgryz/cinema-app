package com.suitt.controllers;

import com.suitt.tables.cinemaShow.CinemaShowService;
import com.suitt.tables.film.FilmService;
import com.suitt.tables.hall.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MainRestController {
    private final FilmService filmService;
    private final HallService hallService;
    private final CinemaShowService cinemaShowService;

    @Autowired
    public MainRestController(FilmService filmService, HallService hallService, CinemaShowService cinemaShowService) {
        this.filmService = filmService;
        this.hallService = hallService;
        this.cinemaShowService = cinemaShowService;
    }

    @GetMapping("/schedule")
    public Map<String, List<?>> getSchedule(){
        Map<String, List<?>> result = new HashMap<>();
        result.put("films", filmService.getAll());
        result.put("halls", hallService.getAll());
        result.put("cinemaShows", cinemaShowService.getNearest(1000));
        return result;
    }
}
