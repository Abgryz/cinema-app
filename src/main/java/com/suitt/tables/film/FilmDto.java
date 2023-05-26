package com.suitt.tables.film;

import lombok.Builder;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@Builder(toBuilder = true)
public record FilmDto(
        Long id,
        String filmName,
        LocalDate rentalDate,
        Time filmDuration,
        String filmDirectorFullName,
        String filmCast,
        List<String> filmGenres,
        List<Long> cinemaShows,
        String description,
        String employee,
        String image
) {
}
