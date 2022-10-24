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
//        GetBookByIdHandler getBookByIdHandler = new GetBookByIdHandler();
//        CreateBookHandler createBookHandler = new CreateBookHandler();
//        UpdateBookHandler updateBookHandler = new UpdateBookHandler();
//        DeleteBookHandler deleteBookHandler = new DeleteBookHandler();

        app.get("/hello", helloHandler);
        app.put("/user", ticketController.addNewUser);
        app.put("/ticket", ticketController.addNewTicket);
        //How do we pass an email of "aa@bb.com" in this URL??
        //http://localhost:8080/user/{email} -- where do we pass "aa@bb.com"?
        //http://localhost:8080/user/aa@bb.com
        app.get("/user/{email}", ticketController.getUserByEmail);
        app.get("/pendingTickets", ticketController.getPendingTickets);
        //We know how to pass an integer id in a path: /books/{id}
        //We know how to pass an email string: /user/{email}
        //How do we pass a complete User object in a path??
        //Answer: I don't know.
        //Practical answer: don't try; instead pass
        app.get("/allTicketsByUserId/{userId}", ticketController.getAllTicketsForUser);
        app.patch("/updateTicketStatus", ticketController.updateTicketStatus);

        app.start();

//        MenuDriver driver = new MenuDriver();
//        driver.runMain();
    }
}
