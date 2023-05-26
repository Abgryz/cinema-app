package com.suitt.controllers.rest;

import com.suitt.models.Response;
import com.suitt.tables.cinemaShow.CinemaShowDto;
import com.suitt.tables.cinemaShow.CinemaShowService;
import com.suitt.tables.film.FilmDto;
import com.suitt.tables.film.FilmService;
import com.suitt.tables.genre.GenreDto;
import com.suitt.tables.genre.GenreService;
import com.suitt.tables.hall.HallDto;
import com.suitt.tables.hall.HallService;
import com.suitt.tables.ticketSales.TicketSalesDto;
import com.suitt.tables.ticketSales.TicketSalesService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins")
public class AdminRestController {
    private final TicketSalesService ticketSalesService;
    private final GenreService genreService;
    private final FilmService filmService;
    private final HallService hallService;
    private final CinemaShowService cinemaShowService;

    @PostMapping("/films")
    @Transactional
    public Response postFilm(@RequestParam("name") String name,
                             @RequestParam("duration") LocalTime duration,
                             @RequestParam("filmDirector") String filmDirector,
                             @RequestParam("cast") String cast,
                             @RequestParam("image") String image,
                             @RequestParam("rentalDate") LocalDate rentalDate,
                             @RequestParam("description") String description,
                             @RequestParam("genres") List<String> genres,
                             Authentication authentication){
        FilmDto filmDto = FilmDto.builder()
                .filmName(name)
                .filmDuration(Time.valueOf(duration))
                .filmDirectorFullName(filmDirector)
                .filmCast(cast)
                .image(image)
                .rentalDate(rentalDate)
                .description(description)
                .filmGenres(genres)
                .employee(authentication.getName())
                .filmGenres(genres)
                .cinemaShows(List.of())
                .build();

        filmService.createWithGenres(filmDto);
        return Response.ok(null);
    }

    @PostMapping("/shows")
    @Transactional
    public Response postCinemaShow(@RequestParam("filmId") Long filmId,
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
        return Response.ok(null);
    }

    @PostMapping("/ticket-sales")
    @Transactional
    public Response createOrUpdateTicketSales(@RequestParam("ticketId") Long ticketId,
                                              @RequestParam("email") String email,
                                              Authentication authentication){

        TicketSalesDto ticketSalesDto = TicketSalesDto.builder()
                .employee(authentication.getName())
                .client(email)
                .ticket(ticketId)
                .isBooking(false)
                .saleDate(LocalDate.now())
                .build();
        if(!ticketSalesService.existsByIdAndClient(ticketId, email) || ticketSalesService.getTicketSales(ticketId).isBooking()){
            ticketSalesService.save(ticketSalesDto);
            System.out.println("TicketSale created or updated");
            return Response.ok(null);
        } else {
            return Response.fail();
        }
    }

    @DeleteMapping("/ticket-sales/{ticketId}")
    @Transactional
    public Response cancelBooking(@PathVariable Long ticketId){
        TicketSalesDto ticketSalesDto = ticketSalesService.getTicketSales(ticketId);
        CinemaShowDto cinemaShowDto = cinemaShowService.getByTicket(ticketId);
        if(ticketSalesDto.isBooking() || cinemaShowDto.dateAndTime().minusMinutes(30).isAfter(LocalDateTime.now())){
            ticketSalesService.delete(ticketId);
            return Response.ok(null);
        } else {
            return Response.fail();
        }
    }

    @PostMapping("/films/{id}")
    @Transactional
    public Response updateFilm(@PathVariable Long id,
                               @RequestParam("name") String name,
                               @RequestParam("duration") LocalTime duration,
                               @RequestParam("filmDirector") String filmDirector,
                               @RequestParam("cast") String cast,
                               @RequestParam("image") String image,
                               @RequestParam("rentalDate") LocalDate rentalDate,
                               @RequestParam("description") String description,
                               @RequestParam("genres") List<String> genres,
                               Authentication authentication){
        FilmDto filmDto = FilmDto.builder()
                .filmName(name)
                .filmDuration(Time.valueOf(duration))
                .filmCast(cast)
                .filmGenres(genres)
                .filmDirectorFullName(filmDirector)
                .image(image)
                .id(id)
                .description(description)
                .employee(authentication.getName())
                .rentalDate(rentalDate)
                .build();
        filmService.updateWithGenres(filmDto);
        return Response.ok(null);
    }

    @DeleteMapping("/films/{id}")
    @Transactional
    public Response deleteFilm(@PathVariable Long id){
        filmService.deleteWithGenres(id);
        return Response.ok(null);
    }

    @DeleteMapping("/shows/{id}")
    @Transactional
    public Response deleteCinemaShow(@PathVariable Long id){
        cinemaShowService.deleteWithTickets(id);
        return Response.ok(null);
    }

    @PostMapping("/shows/{id}")
    @Transactional
    public Response updateCinemaShow(@PathVariable Long id,
                                     @RequestParam("filmId") Long filmId,
                                     @RequestParam("time") LocalTime time,
                                     @RequestParam("date") LocalDate date,
                                     @RequestParam("price") double price,
                                     @RequestParam("hallId") Long hallId){
        CinemaShowDto cinemaShowDto = CinemaShowDto.builder()
                .dateAndTime(LocalDateTime.of(date, time))
                .hallId(hallId)
                .filmId(filmId)
                .tickets(List.of())
                .id(id)
                .build();
        cinemaShowService.updateCinemaShowWithTickets(cinemaShowDto, price);
        return Response.ok(null);
    }
}
