package edu.romoshi.DBTools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtils {
    public static Connection getConnection() {
        String dbURL;
        String dbUser;
        String dbPassword;

        FileInputStream fis;
        Properties properties = new Properties();

        try {
          fis = new FileInputStream("src/main/resources/config.properties");
          properties.load(fis);

          dbURL = properties.getProperty("db_host");
          dbUser = properties.getProperty("db_user");
          dbPassword = properties.getProperty("db_password");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Connection connection;

        try {
            connection = DriverManager.getConnection(dbURL, dbUser, dbPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
