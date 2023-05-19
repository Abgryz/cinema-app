package com.suitt.tables.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query(value = "select * from ticket where cinema_show_id = :cinemaShowId", nativeQuery = true)
    List<Ticket> findByCinemaShow(@Param("cinemaShowId") Long cinemaShowId);
}
