package edu.romoshi.userTools;

public class MasterKey {

    private final String password;
    public MasterKey(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "" + password + "";
    }

    public String getPassword() {
        return password;
    }
}
