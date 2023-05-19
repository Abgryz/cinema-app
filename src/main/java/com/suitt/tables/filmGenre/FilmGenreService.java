package com.suitt.tables.filmGenre;

import com.suitt.tables.film.FilmDto;
import com.suitt.tables.film.FilmRepository;
import com.suitt.tables.genre.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmGenreService {
    private final FilmGenreRepository filmGenreRepository;
    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;

    public List<FilmGenreDto> getFilmGenre(Long filmId){
        return filmGenreRepository.findAll().stream()
                .filter(filmGenre -> filmGenre.getFilmGenrePK().getFilm().getId().equals(filmId))
                .map(FilmGenreService::mapFilmGenre)
                .collect(Collectors.toList());
    }

    public List<FilmGenreDto> getFilmGenre(String genreName){
        return filmGenreRepository.findAll().stream()
                .filter(filmGenre -> filmGenre.getFilmGenrePK().getGenre().getName().equals(genreName))
                .map(FilmGenreService::mapFilmGenre)
                .collect(Collectors.toList());
    }

    public void createGenresForFilm(FilmDto filmDto){
        filmDto.filmGenres().forEach(genre -> filmGenreRepository.save(
                new FilmGenre(
                        new FilmGenrePK(
                                filmRepository.findById(filmDto.id()).orElseThrow(),
                                genreRepository.findById(genre).orElseThrow()
                        )
                )
        ));

    }

    public List<FilmGenreDto> getAll(){
        return filmGenreRepository.findAll().stream()
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
