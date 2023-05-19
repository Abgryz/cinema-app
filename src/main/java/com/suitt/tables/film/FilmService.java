package com.suitt.tables.film;

import com.suitt.tables.cinemaShow.CinemaShow;
import com.suitt.tables.cinemaShow.CinemaShowDto;
import com.suitt.tables.employee.EmployeeRepository;
import com.suitt.tables.filmGenre.FilmGenreRepository;
import com.suitt.tables.filmGenre.FilmGenreService;
import com.suitt.tables.genre.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmRepository filmRepository;
    private final EmployeeRepository employeeRepository;
    private final FilmGenreService filmGenreService;

    public FilmDto getFilm(Long id){
        return filmRepository.findById(id)
                .map(FilmService::mapFilm)
                .orElse(null);
    }

    public FilmDto getByCinemaShow(Long cinemaShowId){
        return filmRepository.findByCinemaShowId(cinemaShowId)
                .map(FilmService::mapFilm)
                .orElse(null);

    }

    public List<FilmDto> getAll(){
        return filmRepository.findAll().stream()
                .map(FilmService::mapFilm)
                .collect(Collectors.toList());
    }

    public List<FilmDto> searchFilms(String query){
        return filmRepository.searchFilms(query).stream()
                .map(FilmService::mapFilm)
                .collect(Collectors.toList());
    }

    public List<FilmDto> latestFilms(int limit){
        return filmRepository.latestFilms(limit).stream()
                .map(FilmService::mapFilm)
                .collect(Collectors.toList());
    }

    public Map<CinemaShowDto, String> getFilmNameToCinemaShows(List<CinemaShowDto> cinemaShows){
        Map<CinemaShowDto, String> result = new LinkedHashMap<>();
        cinemaShows.forEach((cinemaShow -> result.put(cinemaShow, getFilm(cinemaShow.filmId()).filmName())));
        return result;
    }

    public void createWithGenres(FilmDto filmDto){
        Film film = mapFilmDto(filmDto);

        filmGenreService.createGenresForFilm(
                filmDto.toBuilder()
                    .id(filmRepository.saveAndFlush(film).getId())
                    .build()
        );
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
    private Film mapFilmDto(FilmDto filmDto){
        return Film.builder()
                .filmName(filmDto.filmName())
                .filmDuration(filmDto.filmDuration())
                .filmCast(filmDto.filmCast())
                .description(filmDto.description())
                .rentalDate(filmDto.rentalDate())
                .employee(employeeRepository.findById(filmDto.employee())
                        .orElseThrow())
                .cinemaShows(List.of())
                .filmGenres(List.of())
                .filmDirectorFullName(filmDto.filmDirectorFullName())
                .image(filmDto.image())
                .build();
    }
}
