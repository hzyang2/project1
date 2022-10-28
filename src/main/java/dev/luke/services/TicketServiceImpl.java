package dev.luke.services;

import dev.luke.entities.Ticket;
import dev.luke.entities.User;
import dev.luke.repositories.TicketDao;
import java.util.List;

public class TicketServiceImpl implements TicketService{
    private TicketDao ticketDao;

    public TicketServiceImpl(TicketDao ticketDao){
        this.ticketDao = ticketDao;
    }

    @Override
    public User registerUser(User user) { return ticketDao.registerUser(user); }

    @Override
    public User getUserByEmail(String email) {
        return ticketDao.getUserByEmail(email);
    }

    @Override
    public List<Ticket> getPendingTickets() {
        return ticketDao.getPendingTickets();
    }

    @Override
    public Ticket addNewTicket(Ticket ticket) {
        return ticketDao.addNewTicket(ticket);
    }

    @Override
    public Ticket getTicketById(int ticket_id) { return ticketDao.getTicketById(ticket_id); }

    @Override
    public List<Ticket> getAllTicketsForUser(int user_id) {
        return ticketDao.getAllTicketsForUser(user_id);
    }

    @Override
    public void updateTicketStatus(Ticket ticket) {
        ticketDao.updateTicketStatus(ticket);
    }

    @Override
    public User logIn(User user) {
        return ticketDao.logIn(user);
    }
}
