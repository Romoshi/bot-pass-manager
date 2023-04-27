package edu.romoshi.core.dao.accounts;

import edu.romoshi.core.crypto.Decryption;
import edu.romoshi.core.crypto.Encryption;
import edu.romoshi.core.dao.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class Accounts {
    private static final Logger logger = LoggerFactory.getLogger(Accounts.class);
    private final String nameService;
    private final String login;
    private final String password;

    public String getNameService() {
        return nameService;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Accounts(String nameService, String login, String password) {
        this.nameService = nameService;
        this.login = login;
        this.password = password;
    }

    public void addAccount(int id) {
        try(Connection connection = Connector.getNewConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(AccountsQuery.ADD_ACCOUNT)) {
            Encryption en = new Encryption();

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, this.nameService);
            preparedStatement.setString(3, this.login);
            preparedStatement.setString(4, en.encrypt(this.password, String.valueOf(id)));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Add account", e);
        }
    }
    public static List<Accounts> getAccounts(int id) {
        Decryption de = new Decryption();
        List<Accounts> accounts = new ArrayList<>();

        try(Connection connection = Connector.getNewConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(AccountsQuery.READ)) {
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                String ns = rs.getString("name_service");
                String lg = rs.getString("login");
                String pass = de.decrypt(rs.getString("password"), String.valueOf(id));

                accounts.add(new Accounts(ns, lg, pass));
            }
        } catch (Exception e) {
            logger.error("Get accounts", e);
        }

        return accounts;
    }

    public static void deleteAccount(String nameService, int id) {
        try(Connection connection = Connector.getNewConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(AccountsQuery.DELETE)) {

            preparedStatement.setString(1, nameService);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Delete account", e);
        }
    }
}
