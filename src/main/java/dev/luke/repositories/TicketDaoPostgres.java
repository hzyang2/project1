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
            int generatedKey = resultSet.getInt("user_id");
            user.setUser_id(generatedKey);
            return user;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "select * from users where email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            User user = new User();
            user.setUser_id(rs.getInt("user_id"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));
            user.setEmail(email);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Ticket addNewTicket(Ticket ticket) {
        try(Connection connection = ConnectionFactory.getConnection()){
            String sql = "insert into tickets (user_id, amount, description, status)"
                                    + " values(?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, ticket.getUser().getUser_id());
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
    public List<Ticket> getAllTicketsForUser(User user) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "select * from tickets where user_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, user.getUser_id());

            List<Ticket> ticketList = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setAmount(rs.getDouble("amount"));
                ticket.setDescription(rs.getString("description"));
                ticket.setStatus(rs.getString("status"));
                ticketList.add(ticket);
            }
            return ticketList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Purpose: show manager list of pending requests with user email, amount, purpose/description.
     */
    @Override
    public List<Ticket> getPendingTickets() {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "select u.email, t.id, t.amount, t.description"
                        + " from tickets t join users u on u.user_id = t.user_id"
                        + " where status = 'Pending'";
            PreparedStatement ps = connection.prepareStatement(sql);

            List<Ticket> ticketList = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ticket ticket = new Ticket();
                User user = new User();
                user.setEmail(rs.getString("email"));
                ticket.setUser(user);
                ticket.setId(rs.getInt("id"));
                ticket.setAmount(rs.getDouble("amount"));
                ticket.setDescription(rs.getString("description"));
                ticketList.add(ticket);
            }
            return ticketList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
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
