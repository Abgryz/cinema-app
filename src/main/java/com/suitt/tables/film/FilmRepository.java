package com.suitt.tables.film;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
    @Query(value = "select * from search_films(:name)", nativeQuery = true)
    List<Film> searchFilms(@Param("name") String name);

    @Query(value = "select * from film where image is not null order by rental_date desc limit :limit", nativeQuery = true)
    List<Film> latestFilms(@Param("limit") int limit);

    @Query(value = "select * from film where emp_email = :email", nativeQuery = true)
    List<Film> findByEmployee(@Param("email") String email);

    @Query(value = "select * from film where film_id in (select film_id from cinema_show where cinema_show_id = :id)", nativeQuery = true)
    Optional<Film> findByCinemaShowId(@Param("id") Long id);
}
