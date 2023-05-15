package com.suitt.tables.filmGenre;

import lombok.Builder;

@Builder
public record FilmGenreDto(
        Long film,
        String genre
) {
}
