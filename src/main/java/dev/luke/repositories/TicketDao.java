package dev.luke.repositories;

import dev.luke.entities.Ticket;
import dev.luke.entities.User;

import java.util.List;

public interface TicketDao {

    User addNewUser(User user);

    User getUserByEmail(String email);

    Ticket addNewTicket(Ticket ticket);

    List<Ticket> getPendingTickets();

    List<Ticket> getAllTicketsForUser(int user_id);

    Ticket updateTicketStatus(Ticket ticket);
}
