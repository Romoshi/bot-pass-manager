package edu.romoshi.user;

public class AccWhichSave {
    private String nameService;
    private String login;
    private String password;

    public AccWhichSave(String name, String login, String pass) {
        this.nameService = name;
        this.login = login;
        this.password = pass;
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
