package com.suitt.controllers.rest;

import com.suitt.models.Response;
import com.suitt.security.user.UserService;
import com.suitt.tables.cinemaShow.CinemaShowDto;
import com.suitt.tables.cinemaShow.CinemaShowService;
import com.suitt.tables.film.FilmDto;
import com.suitt.tables.film.FilmService;
import com.suitt.tables.genre.GenreDto;
import com.suitt.tables.genre.GenreService;
import com.suitt.tables.hall.HallDto;
import com.suitt.tables.hall.HallService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.beans.Transient;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins")
public class AdminRestController {
    private final GenreService genreService;
    private final FilmService filmService;
    private final HallService hallService;
    private final CinemaShowService cinemaShowService;

    @GetMapping("/films")
    public List<GenreDto> films(){
        return genreService.getAll();
    }

    @PostMapping("/films")
    @Transactional
    public void postFilm(@RequestParam("name") String name,
                             @RequestParam("duration") LocalTime duration,
                             @RequestParam("filmDirector") String filmDirector,
                             @RequestParam("cast") String cast,
                             @RequestParam("image") String image,
                             @RequestParam("rentalDate") LocalDate rentalDate,
                             @RequestParam("description") String description,
                             @RequestParam("genres") List<String> genres){
        FilmDto filmDto = FilmDto.builder()
                .filmName(name)
                .filmDuration(Time.valueOf(duration))
                .filmDirectorFullName(filmDirector)
                .filmCast(cast)
                .image(image)
                .rentalDate(rentalDate)
                .description(description)
                .filmGenres(genres)
                .employee(UserService.authentication().getName())
                .filmGenres(genres)
                .cinemaShows(List.of())
                .build();

        filmService.createWithGenres(filmDto);
//        return Response.ok(null);
    }

    @GetMapping("/cinemashows")
    public List<HallDto> cinemaShows(){
        return hallService.getAll();
    }


    @PostMapping("/cinemashows")
    @Transactional
    public void postCinemaShow(@RequestParam("filmId") Long filmId,
                                   @RequestParam("time") LocalTime time,
                                   @RequestParam("date") LocalDate date,
                                   @RequestParam("hallId") Long hallId,
                                   @RequestParam("price") double price){
        CinemaShowDto cinemaShowDto = CinemaShowDto.builder()
                .tickets(List.of())
                .filmId(filmId)
                .hallId(hallId)
                .dateAndTime(LocalDateTime.of(date, time))
                .build();
        cinemaShowService.createCinemaShowWithTickets(cinemaShowDto, price);
//        return Response.ok(null);
    }
}
