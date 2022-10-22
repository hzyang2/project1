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
//        app.get("/books/{id}", ticketController.getBookByIdHandler);
//        app.post("/books", ticketController.createBook);
//        app.put("/books", ticketController.updateBookHandler);
//        app.delete("/books/{id}", ticketController.deleteBookHandler);
//        app.get("/books/{id}", ticketController.addNewTicket);



//        MenuDriver driver = new MenuDriver();
//        driver.runMain();
        app.start();
    }
}

//changed files