package edu.romoshi.DBTools;

import edu.romoshi.userTools.AccWhichSave;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CRUDUtils {

    public static void showAccounts() {
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLCommands.SHOW)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            //TODO: I think this code need change.
            while (resultSet.next()) {
                System.out.println(resultSet.getString(2) + "\n" +
                        resultSet.getString(3) + "\n" +
                        resultSet.getString(4));
            }
        } catch(SQLException ex) {
            ex.getMessage();
        }
    }
    public static void saveAccount(AccWhichSave account) {
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLCommands.INSERT)) {

            preparedStatement.setString(1, account.getNameService());
            preparedStatement.setString(2, account.getLogin());
            preparedStatement.setString(3, account.getPassword());
            preparedStatement.executeUpdate();
        } catch(SQLException ex) {
            ex.getMessage();
        }
    }

    public static void deleteAccount(String nameService) {
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLCommands.DELETE)) {

            preparedStatement.setString(1, nameService);
            preparedStatement.executeUpdate();
        } catch(SQLException ex) {
            ex.getMessage();
        }
    }
}
