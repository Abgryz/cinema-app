package com.suitt.tables.filmGenre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmGenreRepository extends JpaRepository<FilmGenre, FilmGenrePK> {
}
