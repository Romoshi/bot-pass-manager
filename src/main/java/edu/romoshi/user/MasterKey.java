package edu.romoshi.user;

public final class MasterKey {

    private final String password;
    public MasterKey(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
