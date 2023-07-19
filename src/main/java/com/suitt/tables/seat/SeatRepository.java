package com.suitt.tables.seat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Query(value = """
		select ticket.*, seat.*
		from seat
			join ticket using (seat_id)
			left join ticket_sales using(ticket_id)
		where cinema_show_id = :cinemaShowId
			and ticket_sales.ticket_id is null;
	""", nativeQuery = true)
    List<Object> getSeatData(@Param("cinemaShowId") Long cinemaShowId);
}
