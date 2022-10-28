package dev.luke.daotests;

import dev.luke.entities.Ticket;
import dev.luke.entities.User;
import dev.luke.repositories.TicketDao;
import dev.luke.repositories.TicketDaoPostgres;
import org.eclipse.jetty.util.DateCache;
import org.junit.jupiter.api.*;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TicketDaoTest {

    static TicketDao ticketDao = new TicketDaoPostgres();

    // this is kabob case, tests are written in kabob case
    @Test
    @Order(1)
    void add_new_ticket_test() {
        User user = ticketDao.getUserByEmail("test");//"test" should exist in database.
        Ticket newTicket = new Ticket(0, 100.00, "food", "Pending");
        newTicket.setUser(user);
        Ticket addedTicket = ticketDao.addNewTicket(newTicket);
        Assertions.assertNotEquals(0, addedTicket.getUser().getUser_id());
    }

    @Test
    @Order(2)
    void get_user_by_email_test() {
        User gottenEmail = ticketDao.getUserByEmail("test");
        Assertions.assertEquals("test", gottenEmail.getEmail());
    }

    @Test
    @Order(3)
    void update_ticket_status_test() {
        List<Ticket> tickets = ticketDao.getPendingTickets();
        Ticket ticket = tickets.get(0);
        int ticketId = ticket.getId();

        ticket.setStatus("Rejected");
        ticketDao.updateTicketStatus(ticket);
        ticket = ticketDao.getTicketById(ticketId);
        Assertions.assertEquals("Rejected", ticket.getStatus());
    }

    @Test
    @Order(4)
    void get_pending_tickets_test() {
        List<Ticket> gottenPendingTickets = ticketDao.getPendingTickets();
        Assertions.assertEquals("Pending", gottenPendingTickets.get(0).getStatus());
    }

    @Test
    @Order(5)
    void register_user_test() {
        User newUser = new User(0, "zz", "password", "Employee");//"zz" should not exist in database.
        User addedUser = ticketDao.registerUser(newUser);
        User checkUser = ticketDao.getUserByEmail("zz");
        Assertions.assertEquals(checkUser.getUser_id(), addedUser.getUser_id());
        Assertions.assertEquals(checkUser.getEmail(), addedUser.getEmail());
        Assertions.assertEquals(checkUser.getRole(), addedUser.getRole());
    }

    @Test
    @Order(6)
    void get_ticket_by_id_test() {
        Ticket gottenTicket = ticketDao.getTicketById(35);//35 is test's ticketId
        Assertions.assertEquals(35, gottenTicket.getId());
    }

    @Test
    @Order(7)
    void get_all_tickets_for_user_test() {
        List<Ticket> gottenTickets = ticketDao.getAllTicketsForUser(668); //668 is test's userId
        Assertions.assertNotEquals(0, gottenTickets.size());
    }

    @Test
    @Order(8)
    void log_in_test() {
        User user = new User();
        user.setEmail("test");
        user.setPassword("test");
        User gottenUser = ticketDao.logIn(user);
        Assertions.assertEquals("test", gottenUser.getEmail());
    }
}