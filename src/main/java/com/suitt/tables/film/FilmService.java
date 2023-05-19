package com.suitt.tables.film;

import com.suitt.tables.cinemaShow.CinemaShow;
import com.suitt.tables.cinemaShow.CinemaShowDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmRepository repository;

    public FilmDto getFilm(Long id){
        return repository.findById(id)
                .map(FilmService::mapFilm)
                .orElse(null);
    }

    public FilmDto getByCinemaShow(Long cinemaShowId){
        return repository.findByCinemaShowId(cinemaShowId)
                .map(FilmService::mapFilm)
                .orElse(null);

    }

    public List<FilmDto> getAll(){
        return repository.findAll().stream()
                .map(FilmService::mapFilm)
                .collect(Collectors.toList());
    }

    public List<FilmDto> searchFilms(String query){
        return repository.searchFilms(query).stream()
                .map(FilmService::mapFilm)
                .collect(Collectors.toList());
    }

    public List<FilmDto> latestFilms(int limit){
        return repository.latestFilms(limit).stream()
                .map(FilmService::mapFilm)
                .collect(Collectors.toList());
    }

    public Map<CinemaShowDto, String> getFilmNameToCinemaShows(List<CinemaShowDto> cinemaShows){
        Map<CinemaShowDto, String> result = new LinkedHashMap<>();
        cinemaShows.forEach((cinemaShow -> result.put(cinemaShow, getFilm(cinemaShow.filmId()).filmName())));
        return result;
    }

    private static FilmDto mapFilm(Film film){
        return FilmDto.builder()
                .id(film.getId())
                .filmName(film.getFilmName())
                .filmCast(film.getFilmCast())
                .filmDuration(film.getFilmDuration())
                .filmDirectorFullName(film.getFilmDirectorFullName())
                .rentalDate(film.getRentalDate())
                .cinemaShows(film.getCinemaShows().stream()
                        .map(CinemaShow::getId)
                        .collect(Collectors.toList()))
                .filmGenres(film.getFilmGenres().stream()
                        .map(filmGenre -> filmGenre.getFilmGenrePK()
                                .getGenre()
                                .getName())
                        .collect(Collectors.toList()))
                .image(film.getImage())
                .description(film.getDescription())
                .employee(film.getEmployee().getEmail())
                .build();
    }
}
