package dev.luke.services;
import dev.luke.entities.Ticket;
import dev.luke.entities.User;
import java.util.List;

public interface TicketService {
    User registerUser(User user);
    User getUserByEmail(String email);
    Ticket addNewTicket(Ticket ticket);
    Ticket getTicketById(int ticket_id);
    List<Ticket> getAllTicketsForUser(int user_id);
    List<Ticket> getPendingTickets();
    void updateTicketStatus(Ticket ticket);
    User logIn(User user);
}
