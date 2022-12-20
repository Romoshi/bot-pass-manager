package edu.romoshi.DBTools;

public class SQLCommands {
    static final String SHOW_ACCOUNTS = "SELECT * FROM passwords_db;";
    static final String INSERT_ACCOUNT = "INSERT INTO passwords_db(name_service, login, password) VALUES (?, ?, ?);";
    static final String DELETE_ACCOUNT = "DELETE FROM passwords_db WHERE name_service = ?;";

    static final String CREATE_TABLE = "CREATE TABLE passwords_db (" +
                                "id int auto_increment primary key," +
                                "name_service varchar(60) not null," +
                                "login varchar(60) not null)" +
                                "password varchar(100) not null)";
}
