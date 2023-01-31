package edu.romoshi.database;

import edu.romoshi.user.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class SQLUtils {
    public static void saveAccount(Account account) {
        try(Connection connection = DBUtils.getNewConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLCommands.CREATE)) {

            preparedStatement.setString(1, account.getNameService());
            preparedStatement.setString(2, account.getLogin());
            preparedStatement.setString(3, account.getPassword());
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

    public static void createTable() {
        try(Connection connection = DBUtils.getNewConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLCommands.CREATE_TABLE)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
