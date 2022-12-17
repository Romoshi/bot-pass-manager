package edu.romoshi.JDBC;

public class SQLCommands {
    static final String SHOW = "SELECT name_service, login, password FROM passwords_db WHERE name_service = ?;";
    static final String INSERT = "INSERT INTO passwords_db(name_service, login, password) VALUES (?, ?, ?);";
    static final String DELETE = "DELETE FROM passwords_db WHERE name_service = ?;";
}
