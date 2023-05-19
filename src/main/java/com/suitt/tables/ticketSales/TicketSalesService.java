package com.suitt.tables.ticketSales;

import com.suitt.tables.seat.SeatService;
import com.suitt.tables.ticket.TicketDto;
import com.suitt.tables.ticket.TicketRepository;
import com.suitt.tables.ticket.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketSalesService {
    private final TicketSalesRepository repository;
    private final SeatService seatService;

    public TicketSalesDto getTicketSales(Long id){
        return repository.findById(id)
                .map(TicketSalesService::mapTicketSales)
                .orElse(null);
    }

    public List<TicketSalesDto> getAll(){
        return repository.findAll().stream()
                .map(TicketSalesService::mapTicketSales)
                .collect(Collectors.toList());
    }

    public Map<String, Object> ticketToMap(TicketDto ticketDto){
        Map<String, Object> result = new HashMap<>();
        result.put("ticket", ticketDto);
        result.put("isBusy", repository.findById(ticketDto.id()).orElse(null) != null);
        result.put("seat", seatService.getSeat(ticketDto.seat()));
        return result;
    }

    public List<Map<String, ?>> ticketsToMap(List<TicketDto> tickets){
        return tickets.stream()
                .map(this::ticketToMap)
                .collect(Collectors.toList());
    }

    private static TicketSalesDto mapTicketSales(TicketSales ticketSales){
        return TicketSalesDto.builder()
                .ticket(ticketSales.getTicketSalesPK().getTicket().getId())
                .saleDate(ticketSales.getSaleDate())
                .client(ticketSales.getClient().getEmail())
                .employee(ticketSales.getClient().getEmail())
                .isBooking(ticketSales.isBooking())
                .build();
    }
}
