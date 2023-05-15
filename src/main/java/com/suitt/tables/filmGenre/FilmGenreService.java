package com.suitt.tables.filmGenre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmGenreService {
    private final FilmGenreRepository repository;

    public List<FilmGenreDto> getFilmGenre(Long filmId){
        return repository.findAll().stream()
                .filter(filmGenre -> filmGenre.getFilmGenrePK().getFilm().getId().equals(filmId))
                .map(FilmGenreService::mapFilmGenre)
                .collect(Collectors.toList());
    }

    public List<FilmGenreDto> getFilmGenre(String genreName){
        return repository.findAll().stream()
                .filter(filmGenre -> filmGenre.getFilmGenrePK().getGenre().getName().equals(genreName))
                .map(FilmGenreService::mapFilmGenre)
                .collect(Collectors.toList());
    }

    public FilmGenreDto getFilmGenre(FilmGenrePK filmGenrePK){
        return repository.findById(filmGenrePK)
                .map(FilmGenreService::mapFilmGenre)
                .orElse(null);
    }

    public List<FilmGenreDto> getAll(){
        return repository.findAll().stream()
                .map(FilmGenreService::mapFilmGenre)
                .collect(Collectors.toList());
    }

    private static FilmGenreDto mapFilmGenre(FilmGenre filmGenre){
        return FilmGenreDto.builder()
                .genre(filmGenre.getFilmGenrePK().getGenre().getName())
                .film(filmGenre.getFilmGenrePK().getFilm().getId())
                .build();
    }
}
