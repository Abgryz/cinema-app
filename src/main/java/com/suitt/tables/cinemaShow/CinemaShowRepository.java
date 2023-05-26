package com.suitt.tables.cinemaShow;

import com.suitt.tables.film.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CinemaShowRepository extends JpaRepository<CinemaShow, Long> {
    @Query(value = "select * from cinema_show where date_and_time > now() order by date_and_time limit :limit", nativeQuery = true)
    List<CinemaShow> findNearestCinemaShows(@Param("limit") int limit);

    @Query(value = "select * from cinema_show where date_and_time > now() and film_id = :filmId order by date_and_time limit :limit", nativeQuery = true)
    List<CinemaShow> findNearestByFilmId(@Param("filmId") Long filmId, @Param("limit") int limit);

    @Query(value = "select * from cinema_show where cinema_show_id = :id and date_and_time > now()", nativeQuery = true)
    Optional<CinemaShow> findNotStarted(@Param("id") Long id);

    @Query(value = "select * from cinema_show join ticket using(cinema_show_id) where ticket_id = :ticketId", nativeQuery = true)
    Optional<CinemaShow> findByTicketId(@Param("ticketId") Long ticketId);

    @Query(value = """
            select
                film_name,
                date_and_time,
                duration,
                hall_id,
                hall_type,
                cinema_show_id
            from cinema_show
                join hall using(hall_id)
                join film using(film_id)
            where date_and_time > now()
            order by date_and_time
            """, nativeQuery = true)
    List<Object> findSchedule();

    @Modifying
    @Query(value = """
            UPDATE CinemaShow
            SET dateAndTime=:#{#show.dateAndTime},
                hall=:#{#show.hall},
                film=:#{#show.film}
            WHERE id=:#{#show.id}""")
    void update(@Param("show") CinemaShow cinemaShow);
}
