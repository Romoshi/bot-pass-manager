package edu.romoshi.database;

import edu.romoshi.user.Accounts;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class SQLUtils {
    public static void createUserMk(Message message, String key) {
        try(Connection connection = DBUtils.getNewConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLCommands.CREATE_USER)) {

            preparedStatement.setInt(1, message.getChatId().intValue());
            preparedStatement.setString(2, key);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void saveAccount(Accounts account, Message message) {
        try(Connection connection = DBUtils.getNewConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLCommands.CREATE_PASSWORD)) {

            preparedStatement.setInt(1, message.getChatId().intValue());
            preparedStatement.setString(2, account.getNameService());
            preparedStatement.setString(3, account.getLogin());
            preparedStatement.setString(4, account.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Accounts> getAccounts(Message message) {
        List<Accounts> accounts = new ArrayList<>();

        try(Connection connection = DBUtils.getNewConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLCommands.READ)) {
            preparedStatement.setString(1, message.getChatId().toString());

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                String nameService = rs.getString("name_service");
                String login = rs.getString("login");
                String password = rs.getString("password");

                accounts.add(new Accounts(nameService, login, password));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return accounts;
    }

    public static void deleteAccount(String nameService, Message message) {
        try(Connection connection = DBUtils.getNewConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLCommands.DELETE)) {

            preparedStatement.setString(1, nameService);
            preparedStatement.setString(2, message.getChatId().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createTablePass() {
        try(Connection connection = DBUtils.getNewConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLCommands.CREATE_TABLE_PASS)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createTableUser() {
        try(Connection connection = DBUtils.getNewConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLCommands.CREATE_TABLE_USER)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean userExist(Message message) {
        try(Connection connection = DBUtils.getNewConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLCommands.USER_EXIST)) {


            preparedStatement.setInt(1, message.getChatId().intValue());
            ResultSet rs = preparedStatement.executeQuery();
            if(!rs.next()) return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    public static boolean mkExist(Message message) {
        try(Connection connection = DBUtils.getNewConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLCommands.MK)) {


            preparedStatement.setInt(1, message.getChatId().intValue());
            ResultSet rs = preparedStatement.executeQuery();
            if(!rs.next()) return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    public static String getMk(Message message) {
        String key;

        try(Connection connection = DBUtils.getNewConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLCommands.MK)) {

            preparedStatement.setInt(1, message.getChatId().intValue());
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            key = rs.getString("mk");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return key;
    }
}
