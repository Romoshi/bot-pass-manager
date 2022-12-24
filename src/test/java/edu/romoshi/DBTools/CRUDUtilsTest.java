package edu.romoshi.DBTools;

import edu.romoshi.userTools.AccWhichSave;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

class CRUDUtilsTest {

    @Test
    void saveAccount() throws SQLException {
        CRUDUtils.createTable(CRUDCommands.CREATE_TABLE);

        AccWhichSave google = new AccWhichSave("Google", "ValMaz", "kastvander");
        CRUDUtils.saveAccount(google);
        showResult();

        Connection connection = DBUtils.getNewConnection();
        connection.createStatement().execute("DROP TABLE db_passwords");
    }

    @Test
    void getAccounts() throws SQLException {
        CRUDUtils.createTable(CRUDCommands.CREATE_TABLE);

        AccWhichSave google = new AccWhichSave("Google", "ValMaz", "kastvander");
        AccWhichSave vk = new AccWhichSave("VK", "MazVal", "dervankast");
        CRUDUtils.saveAccount(google);
        CRUDUtils.saveAccount(vk);
        showResult();

        Connection connection = DBUtils.getNewConnection();
        connection.createStatement().execute("DROP TABLE db_passwords");
    }

    @Test
    void deleteAccount() throws SQLException {
        CRUDUtils.createTable(CRUDCommands.CREATE_TABLE);
        AccWhichSave google = new AccWhichSave("Google", "ValMaz", "kastvander");
        AccWhichSave vk = new AccWhichSave("VK", "MazVal", "dervankast");
        CRUDUtils.saveAccount(google);
        CRUDUtils.saveAccount(vk);

        CRUDUtils.deleteAccount("Google");

        showResult();
        Connection connection = DBUtils.getNewConnection();
        connection.createStatement().execute("DROP TABLE db_passwords");
    }

    @Test
    void createTable() throws SQLException {
        CRUDUtils.createTable(CRUDCommands.CREATE_TABLE);
        System.out.println("Table is create!");

        Connection connection = DBUtils.getNewConnection();
        connection.createStatement().execute("DROP TABLE db_passwords");
    }

    private void showResult() {
        for(var i : CRUDUtils.getAccounts(CRUDCommands.READ)) {
            System.out.println(i.getServiceInfo());
            System.out.println("\n");
        }
    }
}