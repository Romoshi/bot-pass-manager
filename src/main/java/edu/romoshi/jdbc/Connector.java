package edu.romoshi.jdbc;

import edu.romoshi.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private static final String URL = "DB_URL";
    private static final String USER ="DB_USER";
    private static final String PASS = "DB_PASS";
    public static Connection connection;
    public static Connection getNewConnection() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASS);
        Log.logger.info("Connection done.");
        return connection;
    }
}
