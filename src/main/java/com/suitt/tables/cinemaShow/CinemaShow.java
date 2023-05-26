package com.suitt.tables.cinemaShow;

import com.suitt.tables.film.Film;
import com.suitt.tables.hall.Hall;
import com.suitt.tables.ticket.Ticket;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "cinema_show")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CinemaShow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cinema_show_id")
    private Long id;

    private Timestamp dateAndTime;

    @ManyToOne
    @JoinColumn(name = "hall_id")
    private Hall hall;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    @OneToMany(mappedBy = "cinemaShow", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Ticket> tickets;
}
