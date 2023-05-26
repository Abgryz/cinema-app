package com.suitt.tables.hall;

import com.suitt.tables.cinemaShow.CinemaShow;
import com.suitt.tables.seat.Seat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HallService {
    private final HallRepository repository;

    public HallDto getHall(Long id){
        return repository.findById(id)
                .map(HallService::mapHall)
                .orElse(null);
    }

    public List<HallDto> getAll(){
        return repository.findAll().stream()
                .map(HallService::mapHall)
                .collect(Collectors.toList());
    }

    public List<HallDto> getHallsFromCinemaShows(List<Long> cinemaShowIds){
        return cinemaShowIds.stream()
                .map(cinemaShowId -> getAll().stream()
                        .filter(hallDto -> hallDto.cinemas().contains(cinemaShowId))
                        .findFirst()
                        .orElse(null))
                .collect(Collectors.toList());
    }

    private static HallDto mapHall(Hall hall) {
        return new HallDto(
                hall.getId(),
                hall.getHallType(),
//                hall.getSeatCount(),
                hall.getCinemaShows().stream()
                        .map(CinemaShow::getId)
                        .collect(Collectors.toList()),
                hall.getSeats().stream()
                        .map(Seat::getId)
                        .collect(Collectors.toList()));
    }
}
