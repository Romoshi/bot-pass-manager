package edu.romoshi.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    private static String dbUrl = System.getenv("DB_URL");
    private static String dbUser = System.getenv("DB_USER");
    private static String dbPassword = System.getenv("DB_PASS");
    public static Connection connection;
    public static Connection getNewConnection() throws SQLException {
        connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        return connection;
    }
}
