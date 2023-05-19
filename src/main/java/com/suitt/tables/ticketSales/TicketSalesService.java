package com.suitt.tables.ticketSales;

import com.suitt.tables.client.ClientRepository;
import com.suitt.tables.seat.SeatService;
import com.suitt.tables.ticket.Ticket;
import com.suitt.tables.ticket.TicketDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketSalesService {
    private final TicketSalesRepository ticketSalesRepository;
    private final SeatService seatService;
    private final ClientRepository clientRepository;

    public TicketSalesDto getTicketSales(Long id){
        return ticketSalesRepository.findById(id)
                .map(TicketSalesService::mapTicketSales)
                .orElse(null);
    }

    public List<TicketSalesDto> getAll(){
        return ticketSalesRepository.findAll().stream()
                .map(TicketSalesService::mapTicketSales)
                .collect(Collectors.toList());
    }

    public void create(Ticket ticket, String email, boolean isBooking){
        ticketSalesRepository.save(
                TicketSales.builder()
                        .ticketSalesPK(new TicketSalesPK(ticket))
                        .client(clientRepository.findById(email).orElseThrow())
                        .isBooking(isBooking)
                        .saleDate(LocalDate.now())
                        .build()
        );
    }

    public Map<String, Object> ticketToMap(TicketDto ticketDto){
        Map<String, Object> result = new HashMap<>();
        result.put("ticket", ticketDto);
        result.put("isBusy", ticketSalesRepository.findById(ticketDto.id()).orElse(null) != null);
        result.put("seat", seatService.getSeat(ticketDto.seat()));
        return result;
    }

    public List<Map<String, ?>> ticketsToMap(List<TicketDto> tickets){
        return tickets.stream()
                .map(this::ticketToMap)
                .collect(Collectors.toList());
    }

//    public List<TicketSalesDto> getBookingByClient(String email){
//        return ticketSalesRepository.findBookingByClient(email).stream()
//                .map(TicketSalesService::mapTicketSales)
//                .collect(Collectors.toList());
//    }

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
