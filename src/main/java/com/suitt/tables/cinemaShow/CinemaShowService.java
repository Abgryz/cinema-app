package com.suitt.tables.cinemaShow;

import com.suitt.tables.ticket.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CinemaShowService {
    private final CinemaShowRepository repository;

    public CinemaShowDto getCinemaShow(Long id){
        return repository.findById(id)
                .map(CinemaShowService::mapCinemaShow)
                .orElse(null);
    }

    public List<CinemaShowDto> getAll(){
        return repository.findAll().stream()
                .map(CinemaShowService::mapCinemaShow)
                .collect(Collectors.toList());
    }

    public List<CinemaShowDto> getNearest(int limit){
        return repository.getNearestCinemaShows(limit).stream()
                .map(CinemaShowService::mapCinemaShow)
                .collect(Collectors.toList());
    }

    public List<CinemaShowDto> getNearestByFilmId(Long filmId, int limit){
        return repository.getNearestByFilmId(filmId, limit).stream()
                .map(CinemaShowService::mapCinemaShow)
                .collect(Collectors.toList());
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
}
