package com.suitt.tables.genre;

import com.suitt.tables.filmGenre.FilmGenre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "genre")
@Data
public class Genre {
    @Id
    @Column(name = "genre_name")
    private String name;

    @OneToMany(mappedBy = "filmGenrePK.genre", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FilmGenre> filmGenres;
}