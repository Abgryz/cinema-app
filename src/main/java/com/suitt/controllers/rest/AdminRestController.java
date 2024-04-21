package com.suitt.controllers.rest;

import com.suitt.tables.cinemaShow.CinemaShowDto;
import com.suitt.tables.cinemaShow.CinemaShowService;
import com.suitt.tables.film.FilmDto;
import com.suitt.tables.film.FilmService;
import com.suitt.tables.ticketSales.TicketSalesDto;
import com.suitt.tables.ticketSales.TicketSalesService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins")
public class AdminRestController {
    private final TicketSalesService ticketSalesService;
    private final FilmService filmService;
    private final CinemaShowService cinemaShowService;

    @PostMapping("/films")
    @Transactional
    public ResponseEntity<String> postFilm(@RequestParam("name") String name,
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
        return ResponseEntity.ok().build();
    }

    @PostMapping("/shows")
    @Transactional
    public ResponseEntity<String> postCinemaShow(@RequestParam("filmId") Long filmId,
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
        return ResponseEntity.ok().build();
    }

    @PostMapping("/ticket-sales")
    @Transactional
    public ResponseEntity<String> createOrUpdateTicketSales(@RequestParam("ticketId") Long ticketId,
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
            log.info("TicketSale created or updated");
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket booking already exists");
        }
    }

    @DeleteMapping("/ticket-sales/{ticketId}")
    @Transactional
    public ResponseEntity<String> cancelBooking(@PathVariable Long ticketId){
        TicketSalesDto ticketSalesDto = ticketSalesService.getTicketSales(ticketId);
        CinemaShowDto cinemaShowDto = cinemaShowService.getByTicket(ticketId);
        if(ticketSalesDto.isBooking() || cinemaShowDto.dateAndTime().minusMinutes(30).isAfter(LocalDateTime.now())){
            ticketSalesService.delete(ticketId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket booking didn`t delete");
        }
    }

    @PostMapping("/films/{id}")
    @Transactional
    public ResponseEntity<String> updateFilm(@PathVariable Long id,
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
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/films/{id}")
    @Transactional
    public ResponseEntity<String> deleteFilm(@PathVariable Long id){
        filmService.delete(id);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/shows/{id}")
    @Transactional
    public ResponseEntity<String> deleteCinemaShow(@PathVariable Long id){
        cinemaShowService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/shows/{id}")
    @Transactional
    public ResponseEntity<String> updateCinemaShow(@PathVariable Long id,
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
        return ResponseEntity.ok().build();
    }
}
