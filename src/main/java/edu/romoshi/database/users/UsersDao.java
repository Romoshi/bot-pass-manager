package edu.romoshi.database.users;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface UsersDao {

    static void addUser(Message message, String key) {}
    static boolean userExist(Message message) {return false;}
    static boolean mkExist(Message message) {return false;}
    static String getMk(Message message) {return "";}
}
