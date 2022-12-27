package edu.romoshi.DBTools;

import edu.romoshi.userTools.AccWhichSave;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

class CRUDUtilsTest {
    private static final String CREATE_TEST = "INSERT INTO db_passwords_test(user_id, name_service, login, password) VALUES(0, ?, ?, ?)";
    private static final String READ_TEST = "SELECT * FROM db_passwords_test";
    private static final String DELETE_TEST = "DELETE FROM db_passwords_test WHERE name_service = ?";

    private static final String CREATE_TABLE_TEST = "CREATE TABLE IF NOT EXISTS db_passwords_test(" +
            "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
            "user_id INT(60) NOT NULL," +
            "name_service VARCHAR(60) NOT NULL," +
            "login VARCHAR(60) NOT NULL," +
            "password VARCHAR(100) NOT NULL);";

    @Test
    void saveAccount() throws SQLException {
        CRUDUtils.createTable(CREATE_TABLE_TEST);

        AccWhichSave google = new AccWhichSave("Google", "ValMaz", "kastvander");
        CRUDUtils.saveAccount(google, CREATE_TEST);
        showResult();

        Connection connection = DBUtils.getNewConnection();
        connection.createStatement().execute("DROP TABLE db_passwords_test");
    }

    @Test
    void getAccounts() throws SQLException {
        CRUDUtils.createTable(CREATE_TABLE_TEST);

        AccWhichSave google = new AccWhichSave("Google", "ValMaz", "kastvander");
        AccWhichSave vk = new AccWhichSave("VK", "MazVal", "dervankast");
        CRUDUtils.saveAccount(google, CREATE_TEST);
        CRUDUtils.saveAccount(vk, CREATE_TEST);
        showResult();

        Connection connection = DBUtils.getNewConnection();
        connection.createStatement().execute("DROP TABLE db_passwords_test");
    }

    @Test
    void deleteAccount() throws SQLException {
        CRUDUtils.createTable(CREATE_TABLE_TEST);
        AccWhichSave google = new AccWhichSave("Google", "ValMaz", "kastvander");
        AccWhichSave vk = new AccWhichSave("VK", "MazVal", "dervankast");
        CRUDUtils.saveAccount(google, CREATE_TEST);
        CRUDUtils.saveAccount(vk, CREATE_TEST);

        CRUDUtils.deleteAccount("Google", DELETE_TEST);

        showResult();
        Connection connection = DBUtils.getNewConnection();
        connection.createStatement().execute("DROP TABLE db_passwords_test");
    }

    @Test
    void createTable() throws SQLException {
        CRUDUtils.createTable(CREATE_TABLE_TEST);
        System.out.println("Table is create!");

        Connection connection = DBUtils.getNewConnection();
        connection.createStatement().execute("DROP TABLE db_passwords_test");
    }

    private void showResult() {
        for(var i : CRUDUtils.getAccounts(READ_TEST)) {
            System.out.println(i.getServiceInfo());
            System.out.println("\n");
        }
    }
}