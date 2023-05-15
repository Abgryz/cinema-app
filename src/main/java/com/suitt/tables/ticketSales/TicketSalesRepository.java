package com.suitt.tables.ticketSales;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketSalesRepository extends JpaRepository<TicketSales, TicketSalesPK> {
}
