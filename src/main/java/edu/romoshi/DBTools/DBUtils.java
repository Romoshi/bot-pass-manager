package edu.romoshi.DBTools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    public static Connection connection;
    public static Connection getNewConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String passwd = "root";
        return DriverManager.getConnection(url, user, passwd);
    }
}
