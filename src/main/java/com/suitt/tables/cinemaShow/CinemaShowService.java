package com.suitt.tables.cinemaShow;

import com.suitt.tables.film.FilmRepository;
import com.suitt.tables.hall.HallRepository;
import com.suitt.tables.ticket.Ticket;
import com.suitt.tables.ticket.TicketRepository;
import com.suitt.tables.ticket.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CinemaShowService {
    private final CinemaShowRepository cinemaShowRepository;
    private final HallRepository hallRepository;
    private final FilmRepository filmRepository;
    private final TicketRepository ticketRepository;
    private final TicketService ticketService;

    public CinemaShowDto getCinemaShow(Long id){
        return cinemaShowRepository.findById(id)
                .map(CinemaShowService::mapCinemaShow)
                .orElse(null);
    }

    public void createCinemaShowWithTickets(CinemaShowDto cinemaShowDto, double price){
        CinemaShow cinemaShow = cinemaShowRepository.saveAndFlush(mapCinemaShowDto(cinemaShowDto));
        ticketService.createAllForCinemaShow(cinemaShow.getId(), price);
    }

    public List<CinemaShowDto> getAll(){
        return cinemaShowRepository.findAll().stream()
                .map(CinemaShowService::mapCinemaShow)
                .collect(Collectors.toList());
    }

    public CinemaShowDto getNotStarted(Long id){
        return cinemaShowRepository.findNotStarted(id)
                .map(CinemaShowService::mapCinemaShow)
                .orElseThrow();
    }

    public List<CinemaShowDto> getNearest(int limit){
        return cinemaShowRepository.findNearestCinemaShows(limit).stream()
                .map(CinemaShowService::mapCinemaShow)
                .collect(Collectors.toList());
    }

    public List<CinemaShowDto> getNearestByFilmId(Long filmId, int limit){
        return cinemaShowRepository.findNearestByFilmId(filmId, limit).stream()
                .map(CinemaShowService::mapCinemaShow)
                .collect(Collectors.toList());
    }

    public CinemaShowDto getByTicket(Long ticketId){
        return cinemaShowRepository.findByTicketId(ticketId)
                .map(CinemaShowService::mapCinemaShow)
                .orElse(null);
    }

    public void deleteWithTickets(Long id){
        ticketRepository.deleteByCinemaShow(id);
        cinemaShowRepository.deleteById(id);
    }

    public List<Object> getSchedule(){
        return cinemaShowRepository.findSchedule();
    }

    public void updateCinemaShowWithTickets(CinemaShowDto cinemaShowDto, double price){
        CinemaShow cinemaShow = mapCinemaShowDto(cinemaShowDto);
        cinemaShow.setId(cinemaShowDto.id());
        ticketRepository.deleteByCinemaShow(cinemaShowDto.id());
        cinemaShowRepository.update(cinemaShow);
        ticketService.createAllForCinemaShow(cinemaShow.getId(), price);
    }

    private static CinemaShowDto mapCinemaShow(CinemaShow cinemaShow){
        return CinemaShowDto.builder()
                .dateAndTime(cinemaShow.getDateAndTime().toLocalDateTime())
                .hallId(cinemaShow.getHall().getId())
                .filmId(cinemaShow.getFilm().getId())
                .id(cinemaShow.getId())
                .tickets(cinemaShow.getTickets().stream()
                        .map(Ticket::getId)
                        .collect(Collectors.toList()))
                .build();
    }

    private CinemaShow mapCinemaShowDto(CinemaShowDto cinemaShowDto){
        return CinemaShow.builder()
                .film(filmRepository.findById(cinemaShowDto.filmId()).orElseThrow())
                .dateAndTime(Timestamp.valueOf(cinemaShowDto.dateAndTime()))
                .hall(hallRepository.findById(cinemaShowDto.hallId()).orElseThrow())
                .tickets(cinemaShowDto.tickets().stream()
                        .map(ticket -> ticketRepository.findById(ticket).orElseThrow())
                        .collect(Collectors.toList()))
                .build();
    }
}
