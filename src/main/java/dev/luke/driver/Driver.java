package dev.luke.driver;
import dev.luke.controllers.TicketController;
//import dev.luke.handler.HelloHandler;
import dev.luke.repositories.TicketDaoPostgres;
import dev.luke.services.TicketService;
import dev.luke.services.TicketServiceImpl;
import io.javalin.Javalin;

public class Driver {
    public static TicketService ticketService = new TicketServiceImpl(new TicketDaoPostgres());
    public static void main(String[] args) {
        Javalin app = Javalin.create();
        TicketController ticketController = new TicketController();
        app.post("/register", ticketController.register);
        app.post("/ticket", ticketController.addNewTicket);
        app.post("/logIn", ticketController.logIn);
        app.get("/pendingTickets", ticketController.getPendingTickets);
        app.get("/ticket/{id}", ticketController.getTicketById);
        app.put("/updateTicketStatus", ticketController.updateTicketStatus);

        app.get("/allTicketsByUserId/{userId}", ticketController.getAllTicketsForUser);
        app.get("/user/{email}", ticketController.getUserByEmail);

        app.start();
    }
}
