package dev.luke.repositories;
import dev.luke.entities.Ticket;
import dev.luke.entities.User;
import dev.luke.util.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDaoPostgres implements TicketDao{
    @Override
    public User registerUser(User user) {
        User searchUser = getUserByEmail(user.getEmail());
        if(searchUser != null){
            return null;
        }
        try(Connection connection = ConnectionFactory.getConnection()){
            String sql = "insert into users values(default, ?, ?, ?)";
            //Three possible outcomes
            // 1)no rows return (typical): register the user
            // 2)password matches (user may have clicked Send twice): don't add another row, but populate user_id
            // 3)password mismatch
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int generatedKey = rs.getInt("user_id");
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
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUser_id(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setEmail(email);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Ticket getTicketById(int ticket_id) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "select * from tickets where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, ticket_id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt("id"));
                User user = new User();
                user.setUser_id(rs.getInt("user_id"));
                ticket.setUser(user);
                ticket.setAmount(rs.getDouble("amount"));
                ticket.setDescription(rs.getString("description"));
                ticket.setStatus(rs.getString("status"));
                return ticket;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
    public List<Ticket> getAllTicketsForUser(int user_id) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "select * from tickets where user_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, user_id);

            List<Ticket> ticketList = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt("id"));
                User user = new User();
                user.setUser_id(rs.getInt("user_id"));
                ticket.setUser(user);
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
    public Ticket updateTicketStatus(Ticket ticket){
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

    @Override
    public User logIn(User user){
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "select user_id, password from users where email = ? ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ResultSet rs = ps.executeQuery();
            if (rs.next() && user.getPassword().equals(rs.getString("password"))) {
                user.setUser_id(rs.getInt("user_id"));
            } else {
                user = null; //This should be handled in client logic as "email is already in use".
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
