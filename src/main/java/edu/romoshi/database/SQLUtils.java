package edu.romoshi.database;

import edu.romoshi.user.Account;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class SQLUtils {
    public static void createUser(Message message) {
        try(Connection connection = DBUtils.getNewConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLCommands.CREATE_USER)) {

            preparedStatement.setString(1, message.getChatId().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void saveAccount(Account account, Message message) {
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

    public static List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>();

        try(Connection connection = DBUtils.getNewConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLCommands.READ)) {
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                String nameService = rs.getString("name_service");
                String login = rs.getString("login");
                String password = rs.getString("password");

                accounts.add(new Account(nameService, login, password));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return accounts;
    }

    public static void deleteAccount(String nameService) {
        try(Connection connection = DBUtils.getNewConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLCommands.DELETE)) {

            preparedStatement.setString(1, nameService);
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
}
