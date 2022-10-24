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
    public User addNewUser(User user) {
        return ticketDao.addNewUser(user);
    }

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
    public List<Ticket> getAllTicketsForUser(int user_id) {
        return ticketDao.getAllTicketsForUser(user_id);
    }

    @Override
    public void updateTicketStatus(Ticket ticket) {
        ticketDao.updateTicketStatus(ticket);
    }
}
