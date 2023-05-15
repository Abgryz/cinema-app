package com.suitt.tables.cinemaShow;

import com.suitt.tables.film.Film;
import com.suitt.tables.hall.Hall;
import com.suitt.tables.ticket.Ticket;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record CinemaShowDto(
        Long id,
        LocalDateTime dateAndTime,
        Long hallId,
        Long filmId,
        List<Long> tickets) {
}
