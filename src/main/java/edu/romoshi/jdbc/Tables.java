package edu.romoshi.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Tables {
    private static final String INIT_TABLE_ACCOUNTS = """
                                                CREATE TABLE IF NOT EXISTS accounts(
                                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                    user_id INT NOT NULL,
                                                    name_service VARCHAR(60) NOT NULL,
                                                    login VARCHAR(60) NOT NULL,
                                                    password VARCHAR(100) NOT NULL,
                                                    FOREIGN KEY (user_id) REFERENCES users (id)
                                                );""";
    private static final String INIT_TABLE_USER = """
                                                CREATE TABLE IF NOT EXISTS users(
                                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                                    user_info INT,
                                                    mk VARCHAR(100)
                                                );""";

    private static void addTable(String query) {
        try(Connection connection = Connector.getNewConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initTables() {
        addTable(INIT_TABLE_USER);
        addTable(INIT_TABLE_ACCOUNTS);
    }
}
