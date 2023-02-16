package edu.romoshi.user;

import edu.romoshi.crypto.Decryption;
import org.telegram.telegrambots.meta.api.objects.Message;

public class AccWhichSave {
    private final String nameService;
    private final String login;
    private final String password;

    public AccWhichSave(String name, String login, String pass) {
        this.nameService = name;
        this.login = login;
        this.password = pass;
    }

    public static String getServices(AccWhichSave account, Message message) throws Exception {
        Decryption de = new Decryption();
        return "Название сервиса: " + account.getNameService() + "\n" +
                "Логин: " + account.getLogin() + "\n" +
                "Пароль: " + de.decrypt(account.getPassword(), message.getChatId().toString());
    }

    public String getNameService() {
        return nameService;
    }

    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }
}
