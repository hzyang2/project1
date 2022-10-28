package dev.luke.controllers;
import com.google.gson.Gson;
import dev.luke.driver.Driver;
import dev.luke.entities.Ticket;
import dev.luke.entities.User;
import io.javalin.http.Handler;
import java.util.List;

public class TicketController {
    private static final String mgrSecret = "mgr";

    public Handler register = (ctx) ->{
        String json = ctx.body();
        Gson gson = new Gson();
        User user = gson.fromJson(json, User.class);
        String role = user.isAuthorizedManager() ? "Manager" : "Employee";
        user.setRole(role);
        User newUser = Driver.ticketService.registerUser(user);
        if(newUser == null){
            ctx.status(409); //This status code is for conflict.
            ctx.result("Email is already in use.");
        }else {
            //DON:T EXPOSE SENSITIVE INFO
            newUser.setAuthorization_secret("");
            newUser.setPassword("");
            String userJason = gson.toJson(newUser);
            ctx.status(201); //This is a status code that will tell us how things went
            ctx.result(userJason);
        }
    };

    static final int NotAcceptable = 406;
    public Handler addNewTicket = (ctx) ->{
        String json = ctx.body();
        Gson gson = new Gson();
        Ticket ticket = gson.fromJson(json, Ticket.class);
        if(ticket.getDescription() == null ||
           ticket.getDescription().equals("") || ticket.getAmount() == 0.0){
            ctx.status(NotAcceptable);
            ctx.result("Your amount or description is missing.");
        }else {
            Ticket newTicket = Driver.ticketService.addNewTicket(ticket);
            String ticketJson = gson.toJson(newTicket);
            ctx.status(201); //This is a status code that will tell us how things went
            ctx.result(ticketJson);
        }
    };

    public Handler getTicketById = (ctx) ->{
        int id = Integer.parseInt(ctx.pathParam("id"));//This will take what value was in the {email} template of the URL path defined in Driver.java.
        Ticket ticket = Driver.ticketService.getTicketById(id);
        Gson gson = new Gson();
        String json = gson.toJson(ticket);
        ctx.result(json);

    };
    public Handler getUserByEmail = (ctx) ->{
        String email = ctx.pathParam("email");//This will take what value was in the {email} template of the URL path defined in Driver.java.
        User updateUser = Driver.ticketService.getUserByEmail(email);
        Gson gson = new Gson();
        String json = gson.toJson(updateUser);
        ctx.result(json);

    };

    static final int notAuthorized = 401;
    public Handler getPendingTickets = (ctx) ->{
        String json = ctx.body();
        Gson gson = new Gson();
        User user = gson.fromJson(json, User.class);
        if(user.isAuthorizedManager()) {
            List<Ticket> pendingTickets = Driver.ticketService.getPendingTickets();
            String json1 = gson.toJson(pendingTickets);
            ctx.result(json1);
        }else{
            ctx.status(notAuthorized);
            ctx.result("You are not authorized.");
        }
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
        User user = ticket.getUser();
        if(user.isAuthorizedManager()){
            Driver.ticketService.updateTicketStatus(ticket);
            ctx.status(204); //This is a status code that will tell us how things went
        }else{
            ctx.status(notAuthorized);
            ctx.result("You are not authorized.");
        }
    };

    public Handler logIn = (ctx) ->{
        String loginjson = ctx.body();
        Gson gson = new Gson();
        User user = gson.fromJson(loginjson, User.class);
        User newUser = Driver.ticketService.logIn(user);
        if(newUser == null){
            ctx.status(409); //This status code is for conflict.
            ctx.result("Your password is wrong.");
        }else {
            String userJason = gson.toJson(newUser);
            ctx.status(201); //This is a status code that will tell us how things went
            ctx.result(userJason);
        }
    };
}
