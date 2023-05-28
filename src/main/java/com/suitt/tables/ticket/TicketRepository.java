package com.suitt.tables.ticket;

import com.suitt.tables.cinemaShow.CinemaShow;
import com.suitt.tables.seat.Seat;
import com.suitt.tables.seat.SeatService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query(value = "select * from ticket where cinema_show_id = :cinemaShowId", nativeQuery = true)
    List<Ticket> findByCinemaShow(@Param("cinemaShowId") Long cinemaShowId);

    Optional<Ticket> findByCinemaShowAndSeat(CinemaShow cinemaShow, Seat seat);

    @Modifying
    @Query(value = "delete from ticket where cinema_show_id = :cinemaShowId", nativeQuery = true)
    void deleteByCinemaShow(@Param("cinemaShowId") Long cinemaShowId);

    @Query(value = """
            select
            	film_name,
            	date_and_time,
            	hall_type,
            	seat.hall_id,
            	seat_row,
            	seat_num,
            	(price * price_coef) as price,
            	ticket.ticket_id
            from ticket
            	join ticket_sales using(ticket_id)
            	join seat using(seat_id)
            	join cinema_show using(cinema_show_id)
            	join film using(film_id)
            	join hall on seat.hall_id = hall.hall_id
            where is_booking = true
            	and cl_email = :email
            """,
            nativeQuery = true)
    List<Object> findTicketBookingDataByClient(@Param("email") String email);

    @Modifying
    @Query(value = "call create_tickets_for_cinema_show(:cinemaShowId, :price);", nativeQuery = true)
    void createAllForCinemaShow(@Param("cinemaShowId") Long cinemaShowId, @Param("price") double price);
}
