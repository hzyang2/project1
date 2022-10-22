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
    public User getAllUsers(String email) {
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
     * If the user is a manager, then show tickets from all users.
     * If the user is an employee, then only show user's tickets.
     *
     * If statusFilter is passed as "Pending", "Approved", or "Rejected",
     * then filter accordingly.
     * but if  statusFilter is passed as "all", include all tickets
     * regardless of status.
     */
    @Override
    public List<Ticket> getTicketsByStatus(String statusFilter, User user) {
        List<Ticket> tickets = new ArrayList<>();
        ticketTable.forEach((k, tkt)-> {
            if ( //filter by status:
                    (tkt.getStatus() == statusFilter //If statusFilter is passed as "Pending", "Approved", or "Rejected",
                            || statusFilter == "all") // but if  statusFilter is passed as "all", include all tickets
                            && //filter by user access (role or id):
                            (user.getRole() == "Manager" ||
                                    user.getUser_id() == tkt.getUser().getUser_id())) {
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

