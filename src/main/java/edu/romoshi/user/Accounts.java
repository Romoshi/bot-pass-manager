package edu.romoshi.user;

import edu.romoshi.crypto.Decryption;
import org.telegram.telegrambots.meta.api.objects.Message;

public class Accounts {
    Accounts account;
    private final String nameService;
    private final String login;
    private final String password;

    public Accounts(String name, String login, String pass) {
        this.nameService = name;
        this.login = login;
        this.password = pass;
    }

    public String getServices(Message message) throws Exception {
        Decryption de = new Decryption();
        return "Название сервиса: " + this.account.getNameService() + "\n" +
                "Логин: " + this.account.getLogin() + "\n" +
                "Пароль: " + de.decrypt(this.account.getPassword(), message.getChatId().toString());
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
