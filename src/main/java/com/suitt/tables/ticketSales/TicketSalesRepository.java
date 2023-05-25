package com.suitt.tables.ticketSales;

import com.suitt.tables.film.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketSalesRepository extends JpaRepository<TicketSales, TicketSalesPK> {
    @Query(value = "select * from ticket_sales where emp_email = :email", nativeQuery = true)
    List<TicketSales> findByEmployee(@Param("email") String email);

    @Query(value = "select * from ticket_sales where ticket_id = :id", nativeQuery = true)
    Optional<TicketSales> findById(@Param("id") Long id);

    @Query(value = "select exists (select * from ticket_sales where ticket_id = :id)", nativeQuery = true)
    boolean existsById(@Param("id") Long id);

    @Query(value = "select exists (select * from ticket_sales where ticket_id = :id and cl_email = :email)", nativeQuery = true)
    boolean existsByIdAndClient(@Param("id") Long id, @Param("email") String email);

    //    @Query(value = "select * from ticket_sales where is_booking = true and cl_email = :email", nativeQuery = true)
//    List<TicketSales> findBookingByClient(@Param("email") String email);

    @Query(value = "delete from ticket_sales where ticket_id = :id", nativeQuery = true)
    void deleteById(@Param("id") Long id);

    @Query(value = """
            select ticket_id,
                cl_email,
                sale_date,
                emp_email,
                is_booking
            from ticket_sales
                join ticket using(ticket_id)
                join cinema_show using(cinema_show_id)
            where is_booking = true and
                date_and_time > now()
            """, nativeQuery = true)
    List<TicketSales> findAllBooking();
}
