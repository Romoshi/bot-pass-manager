package edu.romoshi.jdbc.accounts;

public class AccountsQuery {
    public static final String ADD_ACCOUNT = "INSERT INTO accounts(user_id, name_service, login, password)" +
                                        "VALUES((SELECT users.id FROM users WHERE users.user_info = ?), ?, ?, ?);";
    public static final String READ = "SELECT name_service, login, password" +
            "FROM accounts WHERE accounts.id = (SELECT users.id FROM users WHERE users.user_info = ?);";
    public static final String DELETE = "DELETE FROM accounts WHERE name_service = ?" +
                                                                  "AND (SELECT users.id FROM users WHERE users.user_info = ?);";
}
