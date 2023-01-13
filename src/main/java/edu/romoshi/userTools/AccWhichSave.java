package edu.romoshi.userTools;

import edu.romoshi.crypto.PassCipher;

public class AccWhichSave {
    private String nameService;
    private String login;
    private String password;
    private String decPass;

    public AccWhichSave(String name, String login, String pass) throws Exception {
        PassCipher passCipher = new PassCipher();
        passCipher.init();

        this.nameService = name;
        this.login = login;
        this.password = passCipher.encrypt(pass);
        this.decPass = PassCipher.decrypt(this.password);
    }

    public String getDecPass() {
        return decPass;
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
