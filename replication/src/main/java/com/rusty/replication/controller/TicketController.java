package com.rusty.replication.controller;

import com.rusty.replication.domain.model.Ticket;
import com.rusty.replication.domain.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
1. 티켓 생성
2. 티켓 단건 주문(이메일)
3. 티켓 단전 주문(이름)
4. 티켓 전건 주문
 */

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/getAllTickets")
    public List<Ticket> getAllTickets(){
        return (List<Ticket>) ticketService.getAllTickets();
    }

    @GetMapping("/getTicketById/{ticketId}")
    public Optional<Ticket> getTicketById(@PathVariable Integer ticketId){
        return ticketService.findTicketById(ticketId);
    }

    @GetMapping("/getTicketByEmail/{email:.+}")
    public Ticket getTicketByEmail(@PathVariable String email){
        return ticketService.findTicketByEmail(email);
    }

    @PostMapping("/createTicket")
    public Ticket createTicket(@RequestBody Ticket ticket){
        return ticketService.createTicket(ticket);
    }



}
