package dev.luke.services;

import dev.luke.entities.Ticket;
import dev.luke.entities.User;

import java.util.List;

public interface TicketService {

    User addNewUser(User user);

    User getAllUsers(String email);

    List<Ticket> getTicketsByStatus(String status, User user);

    Ticket addNewTicket(Ticket ticket);

    void saveTicket(Ticket ticket);

}
