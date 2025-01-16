package com.rusty.replication.domain.service.impl;

import com.rusty.replication.domain.model.Ticket;
import com.rusty.replication.domain.service.TicketService;
import com.rusty.replication.repository.dao.TicketDao;
import org.springframework.beans.factory.annotation.Autowired;

public class TicketServiceImpl implements TicketService {

    @Override
    public Iterable<Ticket> getAllTickets() {
        return ticketBookingJpaDao.findAll();
    }
}
