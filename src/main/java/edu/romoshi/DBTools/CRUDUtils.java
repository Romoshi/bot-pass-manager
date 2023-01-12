package edu.romoshi.DBTools;


import edu.romoshi.userTools.AccWhichSave;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CRUDUtils {
    //CREATE
    public static void saveAccount(AccWhichSave account) {
        try(Connection connection = DBUtils.getNewConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CRUDCommands.CREATE)) {

            preparedStatement.setString(1, account.getNameService());
            preparedStatement.setString(2, account.getLogin());
            preparedStatement.setString(3, account.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //READ
    public static List<AccWhichSave> getAccounts() {
        List<AccWhichSave> accounts = new ArrayList<>();

        try(Connection connection = DBUtils.getNewConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CRUDCommands.READ)) {
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                String nameService = rs.getString("name_service");
                String login = rs.getString("login");
                String password = rs.getString("password");

                accounts.add(new AccWhichSave(nameService, login, password));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return accounts;
    }

    //UPDATE...

    //DELETE
    public static void deleteAccount(String nameService) {
        try(Connection connection = DBUtils.getNewConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CRUDCommands.DELETE)) {

            preparedStatement.setString(1, nameService);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createTable() {
        try(Connection connection = DBUtils.getNewConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CRUDCommands.CREATE_TABLE)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
