package dev.luke.controllers;

import com.google.gson.Gson;
import dev.luke.driver.Driver;
import dev.luke.entities.Ticket;
import dev.luke.entities.User;
import io.javalin.http.Handler;
import java.util.List;

public class TicketController {
    public Handler addNewUser = (ctx) ->{
        String json = ctx.body();
        Gson gson = new Gson();
        User user = gson.fromJson(json, User.class);
        User addNewUser = Driver.ticketService.addNewUser(user);
        String userJason = gson.toJson(addNewUser);
        ctx.status(201); //This is a status code that will tell us how things went
        ctx.result(userJason);
    };

    public Handler getUserByEmail = (ctx) ->{
        String email = ctx.pathParam("email");//This will take what value was in the {email} template of the URL path defined in Driver.java.
        User updateUser = Driver.ticketService.getUserByEmail(email);
        Gson gson = new Gson();
        String json = gson.toJson(updateUser);
        ctx.result(json);

    };

    public Handler addNewTicket = (ctx) ->{
        String json = ctx.body();
        Gson gson = new Gson();
        Ticket ticket = gson.fromJson(json, Ticket.class);
        Ticket addNewTicket = Driver.ticketService.addNewTicket(ticket);
        String ticketJson = gson.toJson(addNewTicket);
        ctx.status(201); //This is a status code that will tell us how things went
        ctx.result(ticketJson);
    };
    public Handler getPendingTickets = (ctx) ->{
        Gson gson = new Gson();
        List<Ticket> pendingTickets = Driver.ticketService.getPendingTickets();
        String json = gson.toJson(pendingTickets);
        ctx.result(json);
    };

    public Handler getAllTicketsForUser = (ctx) ->{
        int userId = Integer.parseInt(ctx.pathParam("userId"));
        List<Ticket> tickets = Driver.ticketService.getAllTicketsForUser(userId);
        Gson gson = new Gson();
        String json = gson.toJson(tickets);
        ctx.result(json);
    };

    public Handler updateTicketStatus = (ctx) ->{
        String ticketJSON = ctx.body();
        Gson gson = new Gson();
        Ticket ticket = gson.fromJson(ticketJSON, Ticket.class);
        Driver.ticketService.updateTicketStatus(ticket);
        ctx.status(204); //This is a status code that will tell us how things went
    };
}
