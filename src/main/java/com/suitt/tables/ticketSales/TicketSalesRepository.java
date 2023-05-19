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
}
