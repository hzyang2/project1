package dev.luke.entities;

public class Ticket {
    private Integer id;
    private double amount;

    private User user;
    private String description;
    private String status = "Pending";

    public Ticket() {}

    public Ticket(Integer id, double amount, String description, String status) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.status = status;
    }

    public Ticket(User user, double amount, String description) {
        this.user = user;
        this.amount = amount;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "request_id=" + id +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String showUser(){
        return status
                + " " + amount
                + " " + description;
    }
    public String showMgr() {
        return user.getEmail()
                + " " + amount
                + " " + description;
    }

    public User getUser() {
        return user;
    }

//    public void setUser(String user) {
//        this.user = user;
//    }
}
