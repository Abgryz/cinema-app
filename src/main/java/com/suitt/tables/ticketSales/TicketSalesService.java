package com.suitt.tables.ticketSales;

import com.suitt.tables.ticket.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketSalesService {
    private final TicketSalesRepository repository;

    public TicketSalesDto getTicketSales(TicketSalesPK ticketSalesPK){
        return repository.findById(ticketSalesPK)
                .map(TicketSalesService::mapTicketSales)
                .orElse(null);
    }

    public List<TicketSalesDto> getAll(){
        return repository.findAll().stream()
                .map(TicketSalesService::mapTicketSales)
                .collect(Collectors.toList());
    }

    private static TicketSalesDto mapTicketSales(TicketSales ticketSales){
        return TicketSalesDto.builder()
                .ticket(ticketSales.getTicketSalesPK().getTicket().getId())
                .saleDate(ticketSales.getSaleDate())
                .client(ticketSales.getClient().getPhoneNumber())
                .employee(ticketSales.getClient().getPhoneNumber())
                .isBooking(ticketSales.isBooking())
                .build();
    }
}
