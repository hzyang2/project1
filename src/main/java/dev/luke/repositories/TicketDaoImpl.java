package dev.luke.repositories;

import dev.luke.entities.Ticket;
import dev.luke.entities.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketDaoImpl implements TicketDao{
    private Map<Integer, Ticket> ticketTable = new HashMap();
    private int idCount = 1;

    private Map<String, User> userTable = new HashMap();

    @Override
    public User addNewUser(User user) {
        user.setUser_id(idCount);
        idCount++;
        userTable.put(user.getEmail(), user);
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        return userTable.get(email);
    }

    @Override
    public Ticket addNewTicket(Ticket ticket) {
        ticket.setId(idCount);
        idCount++;
        ticketTable.put(ticket.getId(), ticket);
        return ticket;
    }

    /**
     * For manager view, show Pending tickets from all users.
     */
    @Override
    public List<Ticket> getPendingTickets() {
        List<Ticket> tickets = new ArrayList<>();
        ticketTable.forEach((k, tkt)-> {
            if (tkt.getStatus().equals("Pending")) {
                tickets.add(tkt);
            }
        });
        return tickets;
    }

    @Override
    public List<Ticket> getAllTicketsForUser(User user) {
        List<Ticket> tickets = new ArrayList<>();
        ticketTable.forEach((k, tkt)-> {
            if (user.getUser_id() == tkt.getUser().getUser_id()) {
                tickets.add(tkt);
            }
        });
        return tickets;
    }

    @Override
    public Ticket saveTicket(Ticket ticket) {
        ticketTable.put(ticket.getId(), ticket);
        return ticket;
    }
}

