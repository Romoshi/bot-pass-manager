package edu.romoshi.core.dto.users;

public class UsersQuery {
    public static final String ADD_USER = "INSERT INTO users(user_info, mk) VALUES(?, ?);";
    public static final String USER_EXIST = "SELECT id FROM users WHERE user_info = ?;";
    public static final String GET_MK = "SELECT mk FROM users WHERE user_info = ?;";
}
