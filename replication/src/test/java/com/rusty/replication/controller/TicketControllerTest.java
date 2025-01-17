package com.rusty.replication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rusty.replication.domain.model.Ticket;
import com.rusty.replication.domain.service.TicketService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TicketController.class)
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TicketService ticketService;

    @Test
    public void 티켓발급() throws Exception {

        String URI = "/ticket/createTicket";
        Ticket ticket = new Ticket();
        ticket.setTicketId(101);
        ticket.setPassengerName("kim");
        ticket.setFromStation("seoul");
        ticket.setToStation("busan");
        ticket.setBookingDate(new Date());
        ticket.setEmail("blaze121304@gmail.com");
        String jsonInput = this.convertToJson(ticket);

        //Mock설정
        Mockito.doReturn(ticket).when(ticketService).createTicket(Mockito.any(Ticket.class));

        //테스트 실행
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(URI)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonInput)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        //검증
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        Assert.assertEquals(HttpStatus.OK.value(), mockHttpServletResponse.getStatus());

    }

    @Test
    public void 티켓찾기_ID() throws Exception{
        String URI= "/ticket/getTicketById/{ticketId}";
        Ticket ticket = new Ticket();
        ticket.setTicketId(102);
        ticket.setPassengerName("kim");
        ticket.setFromStation("seoul");
        ticket.setToStation("busan");
        ticket.setBookingDate(new Date());
        ticket.setEmail("blaze121304@gmail.com");
        String jsonInput = this.convertToJson(ticket);

        Mockito.doReturn(Optional.of(ticket)).when(ticketService).findTicketById(102);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(URI, 102)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();

        Assert.assertEquals(HttpStatus.OK.value(), mockHttpServletResponse.getStatus());
        Assert.assertEquals(jsonInput, jsonOutput);
    }




    private String convertToJson(Object ticket) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(ticket);
    }


}
