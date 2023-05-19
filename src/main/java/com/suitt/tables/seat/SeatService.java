package com.suitt.tables.seat;

import com.suitt.tables.ticket.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository repository;

    public SeatDto getSeat(Long id){
        return repository.findById(id)
                .map(SeatService::mapSeat)
                .orElse(null);
    }

    public List<SeatDto> getAll(){
        return repository.findAll().stream()
                .map(SeatService::mapSeat)
                .collect(Collectors.toList());
    }

    private static SeatDto mapSeat(Seat seat){
        return SeatDto.builder()
                .id(seat.getId())
                .seatNumber(seat.getSeatNumber())
                .row(seat.getRow())
                .hall(seat.getHall().getId())
                .priceCoefficient(seat.getPriceCoefficient())
                .tickets(seat.getTickets().stream()
                        .map(Ticket::getId)
                        .collect(Collectors.toList()))
                .build();
    }
}
