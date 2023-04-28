package edu.romoshi.core.dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Tables {
    private static boolean flag = false;

    public static boolean isFlag() {
        return flag;
    }

    private static final Logger logger = LoggerFactory.getLogger(Tables.class);
    private static final String INIT_DB = "CREATE DATABASE IF NOT EXISTS app;";
    private static final String INIT_TABLE_ACCOUNTS = "CREATE TABLE IF NOT EXISTS accounts(" +
                                                    "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                                                    "user_id INT NOT NULL," +
                                                    "name_service VARCHAR(60) NOT NULL," +
                                                    "login VARCHAR(60) NOT NULL," +
                                                    "password VARCHAR(100) NOT NULL," +
                                                    "FOREIGN KEY (user_id) REFERENCES users (id));";
    private static final String INIT_TABLE_USER = "CREATE TABLE IF NOT EXISTS users(" +
                                                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                                                    "user_info INT," +
                                                    "mk VARCHAR(100));";

    private static void executeQuery(String query) {
        try(Connection connection = Connector.getNewConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("From tables - execute query", e);
        }
    }

    public static void initTables() {
        executeQuery(INIT_DB);
        executeQuery(INIT_TABLE_USER);
        executeQuery(INIT_TABLE_ACCOUNTS);
        flag = true;
        logger.info("Tables create.");
    }
}
