package edu.romoshi.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private static final String dbUrl = System.getenv("DB_URL");
    private static final String dbUser = System.getenv("DB_USER");
    private static final String dbPassword = System.getenv("DB_PASS");
    public static Connection connection;
    public static Connection getNewConnection() throws SQLException {
        connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        return connection;
    }
}
