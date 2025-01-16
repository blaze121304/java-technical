package com.rusty.replication.repository.dao;

import com.rusty.replication.domain.model.Ticket;
import org.springframework.data.repository.CrudRepository;

public interface TicketJpaDao extends CrudRepository<Ticket, Integer> {

    Ticket findByEmail(String email);
}
