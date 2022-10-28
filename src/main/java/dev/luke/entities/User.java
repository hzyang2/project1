package dev.luke.entities;

public class User {
    private Integer user_id;
    private String email;
    private String password;
    private String role = "Employee";

    public Boolean isAuthenticated = false;

    private String authorization_secret = "";

    public User() {}

    public User(int user_id, String email, String password, String role) {
        this.user_id = user_id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Boolean getAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(Boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public boolean isAuthorizedManager() {
        return this.authorization_secret.equals("mgr");
    }

    public void setAuthorization_secret(String authorization_secret) {
        this.authorization_secret = authorization_secret;
    }

    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
