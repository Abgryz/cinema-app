package com.suitt.tables.film;

import com.suitt.tables.cinemaShow.CinemaShow;
import com.suitt.tables.employee.Employee;
import com.suitt.tables.filmGenre.FilmGenre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "film")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Film {
    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Long id;

    private LocalDate rentalDate;

    private Time duration;

    private String directorFullName;

    private String filmCast;

    private String filmName;

    private String description;

    @ManyToOne
    @JoinColumn(name = "emp_email")
    private Employee employee;

    private String image;

    @OneToMany(mappedBy = "filmGenrePK.film", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FilmGenre> filmGenres;

    @OneToMany(mappedBy = "film", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CinemaShow> cinemaShows;

//    @OneToOne
//    @JoinColumn(name = "film_id")
//    private Poster poster;
}


