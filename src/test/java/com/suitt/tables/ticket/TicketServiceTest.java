package com.suitt.tables.ticket;

import com.suitt.tables.cinemaShow.CinemaShow;
import com.suitt.tables.cinemaShow.CinemaShowRepository;
import com.suitt.tables.seat.Seat;
import com.suitt.tables.seat.SeatRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class TicketServiceTest {

    private final CinemaShowRepository cinemaShowRepository = Mockito.mock(CinemaShowRepository.class);

    private final SeatRepository seatRepository = Mockito.mock(SeatRepository.class);

    private final TicketRepository ticketRepository = Mockito.mock(TicketRepository.class);

    private final TicketService ticketService = new TicketService(cinemaShowRepository, seatRepository, ticketRepository);

    @Test
    public void testGetTicketBookingDataByClient() {
        String email = "test@example.com";
        Mockito.when(ticketRepository.findTicketBookingDataByClient(email))
                .thenReturn(Collections.emptyList());

        List<Object> result = ticketService.getTicketBookingDataByClient(email);

        assertEquals(0, result.size());
    }

    @Test
    public void testGetTicket() {
        Long ticketId = 1L;
        TicketDto expectedTicketDto = TicketDto.builder()
                .id(ticketId)
                .build();
        Ticket expectedTicket = Ticket.builder()
                .seat(Seat.builder()
                        .build())
                .cinemaShow(CinemaShow.builder()
                        .build())
                .id(ticketId)
                .build();


        Mockito.when(ticketRepository.findById(ticketId))
                .thenReturn(Optional.of(expectedTicket));

        TicketDto result = ticketService.getTicket(ticketId);

        assertEquals(expectedTicketDto, result);
    }

    @Test
    public void testGetByCinemaShow() {
        Long cinemaShowId = 1L;
        Mockito.when(ticketRepository.findByCinemaShow(cinemaShowId))
                .thenReturn(Collections.emptyList());

        List<TicketDto> result = ticketService.getByCinemaShow(cinemaShowId);

        assertEquals(0, result.size());
    }

    @Test
    public void testGetAll() {
        Mockito.when(ticketRepository.findAll())
                .thenReturn(Collections.emptyList());

        List<TicketDto> result = ticketService.getAll();

        assertEquals(0, result.size());
    }

    @Test
    public void testCreateAllForCinemaShow() {
        Long cinemaShowId = 1L;
        double price = 10.0;

        ticketService.createAllForCinemaShow(cinemaShowId, price);

        Mockito.verify(ticketRepository).createTicketsForCinemaShow(eq(cinemaShowId), eq(price));
    }

    @Test
    public void testFindByCinemaShowAndSeat() {
        Long cinemaShowId = 1L;
        Long seatId = 2L;
        TicketDto expectedTicketDto = TicketDto.builder()
                .cinemaShow(cinemaShowId)
                .seat(seatId)
                .build();

        Ticket expectedTicket = Ticket.builder()
                .cinemaShow(CinemaShow.builder()
                        .id(cinemaShowId)
                        .build())
                .seat(Seat.builder()
                        .id(seatId)
                        .build())
                .build();
        Mockito.when(cinemaShowRepository.findById(cinemaShowId))
                .thenReturn(Optional.of(new CinemaShow()));
        Mockito.when(seatRepository.findById(seatId))
                .thenReturn(Optional.of(new Seat()));
        Mockito.when(ticketRepository.findByCinemaShowAndSeat(any(), any()))
                .thenReturn(Optional.of(expectedTicket));

        TicketDto result = ticketService.findByCinemaShowAndSeat(cinemaShowId, seatId);

        assertEquals(expectedTicketDto, result);
    }
}
