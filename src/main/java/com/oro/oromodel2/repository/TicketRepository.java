package com.oro.oromodel2.repository;

import com.oro.oromodel2.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

}