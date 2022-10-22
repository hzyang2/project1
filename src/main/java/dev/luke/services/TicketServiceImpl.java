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
        return null;
    }

    @Override
    public User getAllUsers(String email) {
        return null;
    }

    @Override
    public List<Ticket> getTicketsByStatus(String status, User user) {
        return null;
    }

    @Override
    public Ticket addNewTicket(Ticket ticket) {
        return null;
    }

    @Override
    public void saveTicket(Ticket ticket) {
    }
}
