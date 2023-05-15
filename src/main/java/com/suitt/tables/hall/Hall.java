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
    Long id;

    int seatCount;

    String hallType;

    @OneToMany(mappedBy = "hall", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<CinemaShow> cinemaShows;

    @OneToMany(mappedBy = "hall", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Seat> seats;
}