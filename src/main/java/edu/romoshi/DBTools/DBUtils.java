package edu.romoshi.DBTools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    private static String dbUrl = "jdbc:mysql://localhost:3306/test";
    private static String dbUser = "root";
    private static String dbPassword = "root";
    public static Connection connection;
    public static Connection getNewConnection() throws SQLException {
        connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        return connection;
    }
}
