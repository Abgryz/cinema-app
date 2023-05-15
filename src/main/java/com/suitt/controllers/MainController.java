package com.suitt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suitt.tables.cinemaShow.CinemaShowService;
import com.suitt.tables.film.FilmDto;
import com.suitt.tables.film.FilmService;
import com.suitt.tables.hall.HallService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final FilmService filmService;
    private final CinemaShowService cinemaShowService;
    private final HallService hallService;
    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("newFilms", filmService.latestFilms(5));
        model.addAttribute("nearestCinemaShowsMap", filmService.getFilmNameToCinemaShows(cinemaShowService.getNearest(4)));
        return "home";
    }

    @GetMapping("/films/{id}")
    public String film(Model model, @PathVariable Long id){
        FilmDto film = filmService.getFilm(id);
        if (film.image() == null){
            throw new RuntimeException();
        }

        model.addAttribute("image", film.image());
        model.addAttribute("filmName", film.filmName());
        model.addAttribute("filmGenres",  film.filmGenres().toString().substring(1, film.filmGenres().toString().length() - 1));
        model.addAttribute("filmDirector", film.filmDirectorFullName());
        model.addAttribute("filmCast", film.filmCast());
        model.addAttribute("rentalDate", film.rentalDate());
        model.addAttribute("filmDuration", film.filmDuration());
        model.addAttribute("description", film.description());

        return "film";
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model){
        String str = "Результати пошуку для \"" + query + "\"";
        model.addAttribute("films", filmService.searchFilms(query));
        model.addAttribute("title", str);
        model.addAttribute("h2", str + ":");
        return "films";
    }

    @GetMapping("/films")
    public String films(Model model){
        model.addAttribute("films", filmService.searchFilms(""));
        model.addAttribute("title", "Фільми");
        return "films";
    }

    @GetMapping("/schedule")
    public String schedule(Model model){
        return "schedule";
    }
}
