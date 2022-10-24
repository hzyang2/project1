package dev.luke.services;

import dev.luke.entities.Ticket;
import dev.luke.entities.User;

import java.util.List;

public interface TicketService {

    User addNewUser(User user);

    User getUserByEmail(String email);

    Ticket addNewTicket(Ticket ticket);
    List<Ticket> getAllTicketsForUser(int user_id);
    List<Ticket> getPendingTickets();
    void updateTicketStatus(Ticket ticket);
}
