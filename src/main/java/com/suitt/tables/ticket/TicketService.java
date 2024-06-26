package com.suitt.tables.ticket;

import com.suitt.tables.cinemaShow.CinemaShowRepository;
import com.suitt.tables.seat.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final CinemaShowRepository cinemaShowRepository;
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;

    public List<Object> getTicketBookingDataByClient(String email){
        return ticketRepository.findTicketBookingDataByClient(email);
    }

    public TicketDto getTicket(Long id){
        return ticketRepository.findById(id)
                .map(TicketService::mapTicket)
                .orElse(null);
    }

    public List<TicketDto> getByCinemaShow(Long cinemaShowId){
        return ticketRepository.findByCinemaShow(cinemaShowId).stream()
                .map(TicketService::mapTicket)
                .collect(Collectors.toList());
    }

    public List<TicketDto> getAll(){
        return ticketRepository.findAll().stream()
                .map(TicketService::mapTicket)
                .collect(Collectors.toList());
    }

    public void createAllForCinemaShow(Long cinemaShowId, double price){
//        hallRepository.findById(cinemaShow.getHall().getId()).orElseThrow()
//                .getSeats().forEach(
//                        seat -> ticketRepository.save(
//                                Ticket.builder()
//                                        .seat(seat)
//                                        .cinemaShow(cinemaShow)
//                                        .price(price)
//                                        .build()
//                        )
//                );
        ticketRepository.createTicketsForCinemaShow(cinemaShowId, price);
    }

    public TicketDto findByCinemaShowAndSeat(Long cinemaShowId, Long seatId){
        return ticketRepository.findByCinemaShowAndSeat(
                cinemaShowRepository.findById(cinemaShowId).orElseThrow(),
                seatRepository.findById(seatId).orElseThrow()
        ).map(TicketService::mapTicket).orElse(null);
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
