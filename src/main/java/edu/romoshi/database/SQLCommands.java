package edu.romoshi.database;

public class SQLCommands {
    public static final String CREATE = "INSERT INTO db_passwords(name_service, login, password) VALUES(?, ?, ?)";
    public static final String READ = "SELECT * FROM db_passwords";
    public static final String DELETE = "DELETE FROM db_passwords WHERE name_service = ?";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS db_passwords(" +
                                    "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                                    "name_service VARCHAR(60) NOT NULL," +
                                    "login VARCHAR(60) NOT NULL," +
                                    "password VARCHAR(100) NOT NULL);";
}
