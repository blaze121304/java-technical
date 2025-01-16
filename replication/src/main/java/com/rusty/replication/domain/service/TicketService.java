package com.rusty.replication.domain.service;

import com.rusty.replication.domain.model.Ticket;
import org.springframework.stereotype.Service;

@Service
public interface TicketService {
    Iterable<Ticket> getAllTickets();
}
