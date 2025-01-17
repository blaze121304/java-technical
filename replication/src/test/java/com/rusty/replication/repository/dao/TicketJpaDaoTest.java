package com.rusty.replication.repository.dao;

import com.rusty.replication.controller.TicketController;
import com.rusty.replication.domain.service.TicketService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TicketJpaDao.class)
public class TicketJpaDaoTest {

    @Autowired
    private TicketJpaDao ticketJpaDao;

//    @Autowired
//    private TestEntityManager testEntityManager;
//
//    @Test
//    public void testNewTicket() throws Exception{
//        Ticket ticket = getTicket();
//        Ticket saveInDb = testEntityManager.persist(ticket);
//        Ticket getFromInDb = ticketBookingJpaDao.findOne(saveInDb.getTicketId());
//        assertThat(getFromInDb).isEqualTo(saveInDb);
//    }



}
