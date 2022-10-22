package dev.luke.repositories;

import dev.luke.entities.Ticket;
import dev.luke.entities.User;
import dev.luke.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDaoPostgres implements TicketDao{
    @Override
    public User addNewUser(User user) {
        try(Connection connection = ConnectionFactory.getConnection()){
            String sql = "insert into users values(default, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole());

            //execute is used for create, executeQuery for selecting, executeUpdate for updating.
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int generatedKey = resultSet.getInt("id");
            user.setUser_id(generatedKey);
            return user;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getAllUsers(String email) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "select * from users";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setEmail(email);
                return user;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Ticket addNewTicket(Ticket ticket) {
        try(Connection connection = ConnectionFactory.getConnection()){
            String sql = "insert into tickets values(default, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, ticket.getId());
            preparedStatement.setDouble(2, ticket.getAmount());
            preparedStatement.setString(3, ticket.getDescription());
            preparedStatement.setString(4, ticket.getStatus());

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int generatedKey = resultSet.getInt("id");
            ticket.setId(generatedKey);
            return ticket;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Ticket> getTicketsByStatus(String status, User user) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "select * from tickets where status = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "status");

            ResultSet rs = ps.executeQuery();
            rs.next();

            List<Ticket> ticketList = new ArrayList<>();
            Ticket ticket = new Ticket();
            ticket.setId(rs.getInt("id"));
            ticket.setId(rs.getInt("user_id"));
            ticket.setAmount(rs.getDouble("amount"));
            ticket.setDescription(rs.getString("description"));
            ticket.setStatus(rs.getString("status"));
            ticketList.add(ticket);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public Ticket saveTicket (Ticket ticket){
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "update tickets set status = ? where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ticket.getStatus());
            ps.setInt(2, ticket.getId());

            ps.executeUpdate();
            return ticket;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
