package com.rusty.replication.controller;

import com.rusty.replication.domain.model.Ticket;
import com.rusty.replication.domain.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    private TicketService ticketService;

    @GetMapping("/getAllTickets")
    public List<Ticket> getAllTickets(){
        return (List<Ticket>) ticketService.getAllTickets();
    }

    @GetMapping("/getTicketById/{ticketId}")
    public Ticket getTicketById(@PathVariable Integer ticketId){
        return ticketBookingService.findTicketById(ticketId);
    }

    public String getTicket() {

        return "";
    }

    public String setTicket() {


        return "";
    }



}
