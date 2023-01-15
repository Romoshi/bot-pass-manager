package edu.romoshi.userTools;

import edu.romoshi.crypto.PassCipher;

public class AccWhichSave {
    private String nameService;
    private String login;
    private String password;

    public AccWhichSave(String name, String login, String pass) throws Exception {
        this.nameService = name;
        this.login = login;
        this.password = pass;
    }

    public String getServiceInfo() {
        return "Название сервиса: " + nameService + "\n" +
                "Логин: " + login + "\n" +
                "Пароль: " + password;
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
