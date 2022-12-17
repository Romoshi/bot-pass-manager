package edu.romoshi.DBTools;

import edu.romoshi.userTools.AccWhichSave;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class CRUDUtils {

    public static void showAccount(String nameService) {
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLCommands.SHOW)) {

            preparedStatement.setString(1, nameService);
            preparedStatement.executeUpdate();
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
