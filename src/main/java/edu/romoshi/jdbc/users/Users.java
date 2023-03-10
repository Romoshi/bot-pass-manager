package edu.romoshi.jdbc.users;

import edu.romoshi.Log;
import edu.romoshi.jdbc.Connector;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Users {
    private final int userId;
    public Users(Message message) {
        this.userId = message.getChatId().intValue();
    }

    public void addUser(String key) {
        try(Connection connection = Connector.getNewConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UsersQuery.ADD_USER)) {

            preparedStatement.setInt(1, this.userId);
            preparedStatement.setString(2, key);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Log.logger.error("From Users class, add user", e);
        }
    }
    public boolean userExist() {
        try(Connection connection = Connector.getNewConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UsersQuery.USER_EXIST)) {


            preparedStatement.setInt(1, this.userId);
            ResultSet rs = preparedStatement.executeQuery();
            if(!rs.next()) return true;
        } catch (Exception e) {
            Log.logger.error("From Users class, check user", e);
        }

        return false;
    }
    public String getMk() {
        String mk = null;

        try(Connection connection = Connector.getNewConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UsersQuery.GET_MK)) {

            preparedStatement.setInt(1, this.userId);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) mk = rs.getString("mk");
        } catch (Exception e) {
            Log.logger.error("From Users class, get user mk", e);
        }
        return mk;
    }
}
