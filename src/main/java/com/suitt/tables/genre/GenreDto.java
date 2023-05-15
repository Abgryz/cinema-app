package com.suitt.tables.genre;

import lombok.Builder;

import java.util.List;

@Builder
public record GenreDto(
        String name,
        List<Long> filmGenres
) {
}
