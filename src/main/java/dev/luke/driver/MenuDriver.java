//package dev.luke.driver;
//
//import dev.luke.entities.Ticket;
//import dev.luke.entities.User;
//import dev.luke.menu.MainMenu;
//import dev.luke.menu.Menu;
//import dev.luke.repositories.TicketDao;
//import dev.luke.repositories.TicketDaoPostgres;
//
//import java.util.List;
//import java.util.Scanner;
//
//public class MenuDriver {
//    TicketDao ticketDao = new TicketDaoPostgres();
//    MainMenu mainMenu = new MainMenu(ticketDao);
//    Scanner scan = new Scanner(System.in);
//
//    public MenuDriver() { }
//
//    private User registerOrLogin(User user) {
//        Menu menu = new Menu("LoggedOut", "Welcome to Reimbursement-Land");
//        menu.add(1, "Register", "Register");
//        menu.add(2, "Login", "Login");
//        menu.add(3, "Exit", "Exit");
//        System.out.println(menu);
//
//        int selection = scan.nextInt();
//
//        String password, email;
//        switch (selection) {
//            case 1:
//                user = mainMenu.registerUser();
//                break;
//            case 2:
//                user = mainMenu.loginUser();
//                break;
//            case 3: // Exit
//                System.exit(0);
//        }
//        return user;
//    }
//
//    private User serveEmployee(User user) {
//        Menu menu = new Menu("Employee", "Employee options");
//        menu.add(1, "View all tickets", "all");
//        menu.add(2, "View Pending tickets", "Pending");
//        menu.add(3, "View Approved tickets", "Approved");
//        menu.add(4, "View Rejected tickets", "Rejected");
//        menu.add(5, "Add new Ticket (Reimb Request)", "Add new Ticket (Reimb Request)");
//        menu.add(6, "Log Out", "Log Out");
//        System.out.println(menu);
//        int selection = scan.nextInt();
//        switch (selection) {
//            case 1:
//            case 2:
//            case 3:
//            case 4:
//                mainMenu.viewTickets(selection, menu, user);
//                break;
//            case 5:
//                mainMenu.addTicket(user);
//                break;
//            case 6:
//                user.isAuthenticated = false;
//                System.out.println("Log out.");
//                break;
//        }
//        return user;
//    }
//
//    private User serveManager(User user) {
//        Menu menu = new Menu("Manager", "Manager options");
//        List<Ticket> tickets = ticketDao.getPendingTickets();
//        Boolean somePending = (tickets != null && tickets.size() > 0);
//        Ticket lastTicket = new Ticket();
//        if (somePending) {
//            for (Ticket tkt : tickets)
//                System.out.println(tkt.showMgr());
//            int lastTicketIndex = tickets.size() - 1;
//            lastTicket = tickets.get(lastTicketIndex);
//            menu.add(1, "Approve (last ticket)", "Approved");
//            menu.add(2, "Reject (last ticket)", "Rejected");
//            menu.add(3, "Log Out", "Log Out");
//            System.out.println(menu);
//            int selection = scan.nextInt();
//            switch (selection) {
//                case 1:
//                case 2:
//                    String statusToSet = menu.getMenuCode(selection - 1); //ArrayList is index-0.
//                    lastTicket.setStatus(statusToSet);
//                    ticketDao.updateTicketStatus(lastTicket);
//                    break;
//                case 3:
//                    user.isAuthenticated = false;
//                    System.out.println("Log out.");
//                    break;
//            }
//        }
//        else {
//            System.out.println("(no pending tickets)");
//            System.out.println("1. Log out");
//            String selection = scan.next();
//            user.isAuthenticated = false;
//        }
//        return user;
//    }
//
//    public void runMain() {
//        System.out.println("--Welcome to Expense Reimbursement Program--");
//        User user = new User();
//        boolean running = true;
//
//        while (running) {
//            if (!user.isAuthenticated) {
//                user = registerOrLogin(user);
//            }
//            if (user.isAuthenticated && user.getRole().equals("Employee")) {
//                user = serveEmployee(user);
//            }
//            if (user.isAuthenticated && user.getRole().equals("Manager")) {
//                user = serveManager(user);
//            }
//        }
//    }
//}
