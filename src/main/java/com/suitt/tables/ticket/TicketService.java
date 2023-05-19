package com.suitt.tables.ticket;

import com.suitt.tables.cinemaShow.CinemaShow;
import com.suitt.tables.cinemaShow.CinemaShowDto;
import com.suitt.tables.hall.HallRepository;
import com.suitt.tables.hall.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository repository;
    private final HallRepository hallRepository;

    public TicketDto getTicket(Long id){
        return repository.findById(id)
                .map(TicketService::mapTicket)
                .orElse(null);
    }

    public List<TicketDto> getByCinemaShow(Long cinemaShowId){
        return repository.findByCinemaShow(cinemaShowId).stream()
                .map(TicketService::mapTicket)
                .collect(Collectors.toList());
    }

    public List<TicketDto> getAll(){
        return repository.findAll().stream()
                .map(TicketService::mapTicket)
                .collect(Collectors.toList());
    }

    public void createAllForCinemaShow(CinemaShow cinemaShow, double price){
        hallRepository.findById(cinemaShow.getHall().getId()).orElseThrow()
                .getSeats().forEach(
                        seat -> repository.save(
                                Ticket.builder()
                                        .seat(seat)
                                        .cinemaShow(cinemaShow)
                                        .price(price)
                                        .build()
                        )
                );
    }

    public static TicketDto mapTicket(Ticket ticket){
        return TicketDto.builder()
                .price(ticket.getPrice())
                .id(ticket.getId())
                .seat(ticket.getSeat().getId())
                .cinemaShow(ticket.getCinemaShow().getId())
                .build();
    }

}
