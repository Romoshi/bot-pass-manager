package edu.romoshi.DBTools;

public class SQLCommands {
    static final String SHOW = "SELECT * FROM passwords_db;";
    static final String INSERT = "INSERT INTO passwords_db(name_service, login, password) VALUES (?, ?, ?);";
    static final String DELETE = "DELETE FROM passwords_db WHERE name_service = ?;";
}
