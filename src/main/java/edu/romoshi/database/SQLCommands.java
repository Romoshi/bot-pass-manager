package edu.romoshi.database;

public class SQLCommands {

    public static final String CREATE_PASSWORD = """
                                        INSERT INTO passwords(user_id, name_service, login, password)
                                        VALUES((SELECT users.id FROM users WHERE users.user_info = ?), ?, ?, ?);
                                        """;
    public static final String CREATE_USER = "INSERT INTO users(user_info, mk) VALUES(?, ?);";
    public static final String READ = "SELECT * FROM passwords WHERE (SELECT users.id FROM users WHERE users.user_info = ?);";
    public static final String DELETE = """
                                            DELETE FROM passwords WHERE name_service = ?
                                                                  AND (SELECT users.id FROM users WHERE users.user_info = ?);""";
    public static final String CREATE_TABLE_PASS = """
                                                CREATE TABLE IF NOT EXISTS passwords(
                                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                    user_id INT NOT NULL,
                                                    name_service VARCHAR(60) NOT NULL,
                                                    login VARCHAR(60) NOT NULL,
                                                    password VARCHAR(100) NOT NULL,
                                                    FOREIGN KEY (user_id) REFERENCES users (id)
                                                );""";
    public static final String CREATE_TABLE_USER = """
                                                CREATE TABLE IF NOT EXISTS users(
                                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                                    user_info INT,
                                                    mk VARCHAR(100)
                                                );""";
    public static final String USER_EXIST = "SELECT id FROM users WHERE user_info = ?;";

}
