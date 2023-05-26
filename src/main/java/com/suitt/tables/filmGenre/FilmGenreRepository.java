package com.suitt.tables.filmGenre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmGenreRepository extends JpaRepository<FilmGenre, FilmGenrePK> {
    @Modifying
    @Query(value = "delete from film_genre where film_id = :id", nativeQuery = true)
    void deleteByFilmId(@Param("id") Long filmId);
}
