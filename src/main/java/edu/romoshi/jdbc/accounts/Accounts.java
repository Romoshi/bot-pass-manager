package edu.romoshi.jdbc.accounts;

import edu.romoshi.crypto.Decryption;
import edu.romoshi.jdbc.Connector;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class Accounts {
    private final String nameService;
    private final String login;
    private final String password;


    public Accounts(String nameService, String login, String password) {
        this.nameService = nameService;
        this.login = login;
        this.password = password;
    }

    public void addAccount(Message message) {
        try(Connection connection = Connector.getNewConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(AccountsQuery.ADD_ACCOUNT)) {

            preparedStatement.setInt(1, message.getChatId().intValue());
            preparedStatement.setString(2, this.nameService);
            preparedStatement.setString(3, this.login);
            preparedStatement.setString(4, this.password);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Accounts> getAccounts(Message message) {
        List<Accounts> accounts = new ArrayList<>();

        try(Connection connection = Connector.getNewConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(AccountsQuery.READ)) {
            preparedStatement.setInt(1, message.getChatId().intValue());

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                String ns = rs.getString("name_service");
                String lg = rs.getString("login");
                String pass = rs.getString("password");

                accounts.add(new Accounts(ns, lg, pass));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return accounts;
    }

    public static void deleteAccount(String nameService, Message message) {
        try(Connection connection = Connector.getNewConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(AccountsQuery.DELETE)) {

            preparedStatement.setString(1, nameService);
            preparedStatement.setInt(2, message.getChatId().intValue());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getInfo(Message message) throws Exception {
        Decryption de = new Decryption();
        return "Название сервиса: " + this.nameService + "\n" +
                "Логин: " + this.login + "\n" +
                "Пароль: " + de.decrypt(this.password, message.getChatId().toString());
    }
}
