package com.suitt.tables.hall;

import com.suitt.tables.cinemaShow.CinemaShow;
import com.suitt.tables.seat.Seat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "hall")
public class Hall{
    @Id
    @Column(name = "hall_id")
    private Long id;

    @OneToMany(mappedBy = "hall", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CinemaShow> cinemaShows;

    @OneToMany(mappedBy = "hall", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Seat> seats;
}