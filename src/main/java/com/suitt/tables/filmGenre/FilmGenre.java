package com.suitt.tables.filmGenre;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "film_genre")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmGenre {
    @EmbeddedId
    private FilmGenrePK filmGenrePK;
}

