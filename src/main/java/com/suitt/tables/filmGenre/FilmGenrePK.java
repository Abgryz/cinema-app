package com.suitt.tables.filmGenre;

import com.suitt.tables.film.Film;
import com.suitt.tables.genre.Genre;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmGenrePK implements Serializable {
//    @Column(name = "film_id")
    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

//    @Column(name = "genre_name")
    @ManyToOne
    @JoinColumn(name = "genre_name")
    private Genre genre;
}
