package com.suitt.tables.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository repository;

    public GenreDto getGenre(String name){
        return repository.findById(name)
                .map(GenreService::mapGenre)
                .orElse(null);
    }

    public List<GenreDto> getAll(){
        return repository.findAll().stream()
                .map(GenreService::mapGenre)
                .collect(Collectors.toList());
    }

    private static GenreDto mapGenre(Genre genre){
        return GenreDto.builder()
                .name(genre.getName())
                .filmGenres(genre.getFilmGenres().stream()
                        .map(filmGenre -> filmGenre.getFilmGenrePK()
                                .getFilm()
                                .getId())
                        .collect(Collectors.toList()))
                .build();
    }
}
