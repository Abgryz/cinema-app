package com.suitt.tables.ticketSales;

import com.suitt.tables.cinemaShow.CinemaShowRepository;
import com.suitt.tables.client.ClientRepository;
import com.suitt.tables.employee.EmployeeRepository;
import com.suitt.tables.seat.SeatRepository;
import com.suitt.tables.seat.SeatService;
import com.suitt.tables.ticket.TicketDto;
import com.suitt.tables.ticket.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketSalesService {
    private final EmployeeRepository employeeRepository;
    private final CinemaShowRepository cinemaShowRepository;
    private final SeatRepository seatRepository;
    private final TicketSalesRepository ticketSalesRepository;
    private final TicketRepository ticketRepository;
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

    public List<TicketSalesDto> getAllBooking(){
        return ticketSalesRepository.findAllBooking().stream()
                .map(TicketSalesService::mapTicketSales)
                .collect(Collectors.toList());
    }

    public void save(TicketSalesDto ticketSalesDto){
        ticketSalesRepository.save(
                TicketSales.builder()
                        .ticketSalesPK(
                                new TicketSalesPK(ticketRepository.findById(ticketSalesDto.ticket()).orElseThrow())
                        )
                        .client(clientRepository.findById(ticketSalesDto.client()).orElseThrow())
                        .isBooking(ticketSalesDto.isBooking())
                        .saleDate(ticketSalesDto.saleDate())
                        .employee(ticketSalesDto.employee() == null ? null : employeeRepository.findById(ticketSalesDto.employee()).orElse(null))
                        .build()
        );
    }

    public void delete(Long ticketId){
        ticketSalesRepository.deleteById(ticketId);
    }

    public boolean existsByIdAndClient(Long id, String email){
        return ticketSalesRepository.existsByIdAndClient(id, email);
    }

    public Map<String, Object> ticketToMap(TicketDto ticketDto){
        Map<String, Object> result = new HashMap<>();
        result.put("ticket", ticketDto);
        result.put("isBusy", ticketSalesRepository.existsById(
                new TicketSalesPK(ticketRepository.findById(ticketDto.id()).orElse(null))
        ));
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
