package com.rusty.replication.domain.service;

import com.rusty.replication.domain.model.Ticket;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface TicketService {
    Iterable<Ticket> getAllTickets();
    Optional<Ticket> findTicketById(Integer ticketId);
    Ticket findTicketByEmail(String email);
    Ticket createTicket(Ticket ticket);
}
