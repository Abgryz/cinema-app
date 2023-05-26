package com.suitt.controllers;

import com.suitt.security.user.Role;
import com.suitt.security.user.UserDto;
import com.suitt.security.user.UserService;
import com.suitt.tables.cinemaShow.CinemaShowDto;
import com.suitt.tables.cinemaShow.CinemaShowService;
import com.suitt.tables.film.FilmDto;
import com.suitt.tables.film.FilmService;
import com.suitt.tables.hall.HallService;
import com.suitt.tables.ticket.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class GetController {
    private final FilmService filmService;
    private final CinemaShowService cinemaShowService;

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("newFilms", filmService.latestFilms(5));
        model.addAttribute("nearestCinemaShowsMap", filmService.getFilmNameToCinemaShows(cinemaShowService.getNearest(4)));
        return "home";
    }

    @GetMapping("/films/{id}")
    public String film(Model model, @PathVariable Long id, Authentication authentication){
        FilmDto film = filmService.getFilm(id);
        if (film.image() == null){
            throw new RuntimeException();
        }
        model.addAttribute("film", film);
        model.addAttribute("filmGenres",  film.filmGenres().toString().substring(1, film.filmGenres().toString().length() - 1));
        if (UserService.getRoles(authentication).contains(Role.ROLE_MANAGER.name())){
            model.addAttribute("isManager", true);
        }
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
    public String schedule(Model model, Authentication authentication){
        List<Object> data = cinemaShowService.getSchedule();
        model.addAttribute("data", data);
        if (UserService.getRoles(authentication).contains(Role.ROLE_MANAGER.name())){
            model.addAttribute("isManager", true);
        }
        return "schedule";
    }

    @GetMapping("/schedule/{id}")
    public String tickets(Model model, @PathVariable Long id){
        FilmDto filmDto = filmService.getByCinemaShow(id);
        model.addAttribute("film", filmDto);
        model.addAttribute("cinemaShow", cinemaShowService.getNotStarted(id));
        model.addAttribute("description", filmDto.description());
        return "seats";
    }
}
