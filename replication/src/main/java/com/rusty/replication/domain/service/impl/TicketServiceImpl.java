package com.rusty.replication.domain.service.impl;

import com.rusty.replication.domain.model.Ticket;
import com.rusty.replication.domain.service.TicketService;
import com.rusty.replication.repository.dao.TicketJpaDao;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ConcurrentModificationException;
import java.util.Optional;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {

    @Autowired
    private final TicketJpaDao ticketJpaDao;

    public TicketServiceImpl(TicketJpaDao ticketJpaDao) {
        this.ticketJpaDao = ticketJpaDao;
    }

    @Override
    public Iterable<Ticket> getAllTickets() {
        return ticketJpaDao.findAll();
    }

    @Override
    public Optional<Ticket> findTicketById(Integer ticketId) {
        return ticketJpaDao.findById(ticketId);
    }

    @Override
    public Ticket findTicketByEmail(String email) {
        return ticketJpaDao.findByEmail(email);
    }

    @Override
    public Ticket createTicket(Ticket ticket) {
        try{
            return ticketJpaDao.save(ticket);
        } catch (OptimisticLockException e) {
            throw new ConcurrentModificationException("Ticket was updated by another user. Please refresh and try again.");
        }
    }
}
