package edu.romoshi.userTools;

public class AccWhichSave {
    private String nameService;
    private String login;
    private String password;
    public AccWhichSave(String name, String login, String pass) {
        this.nameService = name;
        this.login = login;
        this.password = pass;
    }

    public String getServiceInfo() {
        String info = nameService + "\n" + login + "\n" + password;
        return info;
    }

    public void setNameService(String nameService) {
        this.nameService = nameService;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
}
