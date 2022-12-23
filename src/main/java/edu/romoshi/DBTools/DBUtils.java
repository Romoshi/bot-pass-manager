package edu.romoshi.DBTools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    private static String dbUrl;
    private static String dbUser;
    private static String dbPassword;
    public static Connection connection;
    public static Connection getNewConnection() throws SQLException {
        dbUrl = "jdbc:mysql://localhost:3306/test";
        dbUser = "root";
        dbPassword = "root";

        connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        return connection;
    }
}
