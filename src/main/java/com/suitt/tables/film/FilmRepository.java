package com.suitt.tables.film;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
    @Query(value = "select * from search_films(:name)", nativeQuery = true)
    List<Film> searchFilms(@Param("name") String name);

    @Query(value = "select * from film where image is not null order by rental_date desc limit :limit", nativeQuery = true)
    List<Film> latestFilms(@Param("limit") int limit);
}
