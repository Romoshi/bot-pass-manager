package edu.romoshi.DBTools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    private static String dbURL = "jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'classpath:init.sql'";
    private static String dbUser = "sa";
    private static String dbPassword = "";
    public static Connection getConnection() {
        Connection connection;

        try {
            connection = DriverManager.getConnection(dbURL, dbUser, dbPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
