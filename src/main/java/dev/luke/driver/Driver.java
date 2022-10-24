package dev.luke.driver;

import dev.luke.controllers.TicketController;
import dev.luke.handler.HelloHandler;
import dev.luke.repositories.TicketDaoPostgres;
import dev.luke.services.TicketService;
import dev.luke.services.TicketServiceImpl;
import io.javalin.Javalin;

public class Driver {
    public static TicketService ticketService = new TicketServiceImpl(new TicketDaoPostgres());
    public static void main(String[] args) {
        Javalin app = Javalin.create();

        HelloHandler helloHandler = new HelloHandler();
        TicketController ticketController = new TicketController();
        app.get("/hello", helloHandler);
        app.put("/user", ticketController.addNewUser);
        app.put("/ticket", ticketController.addNewTicket);
        app.get("/user/{email}", ticketController.getUserByEmail);
        app.get("/pendingTickets", ticketController.getPendingTickets);
        app.get("/allTicketsByUserId/{userId}", ticketController.getAllTicketsForUser);
        app.patch("/updateTicketStatus", ticketController.updateTicketStatus);

        app.start();

//        MenuDriver driver = new MenuDriver();
//        driver.runMain();
    }
}
