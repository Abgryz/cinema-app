package com.suitt.controllers;

import com.suitt.security.user.UserDto;
import com.suitt.security.user.UserService;
import com.suitt.tables.cinemaShow.CinemaShowService;
import com.suitt.tables.film.FilmService;
import com.suitt.tables.hall.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MainRestController {
    private final FilmService filmService;
    private final HallService hallService;
    private final CinemaShowService cinemaShowService;
    private final UserService userService;

//    @Autowired
//    public MainRestController(FilmService filmService, HallService hallService, CinemaShowService cinemaShowService) {
//        this.filmService = filmService;
//        this.hallService = hallService;
//        this.cinemaShowService = cinemaShowService;
//    }

    @GetMapping("/schedule")
    public Map<String, List<?>> getSchedule(){
        Map<String, List<?>> result = new HashMap<>();
        result.put("films", filmService.getAll());
        result.put("halls", hallService.getAll());
        result.put("cinemaShows", cinemaShowService.getNearest(1000));
        return result;
    }

    @GetMapping("/film/{id}")
    public Map<String, Object> getFilmSchedule(@PathVariable("id") Long id){
        Map<String, Object> result = new HashMap<>();
        var film = filmService.getFilm(id);
        result.put("film", film);
        result.put("cinemaShows", cinemaShowService.getNearestByFilmId(id, 1000));
        result.put("halls", hallService.getHallsFromCinemaShows(film.cinemaShows()));
        return result;
    }
}
