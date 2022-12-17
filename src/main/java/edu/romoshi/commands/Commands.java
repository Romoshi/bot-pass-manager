package edu.romoshi.commands;

import edu.romoshi.crypto.PassCipher;
import edu.romoshi.userTools.AccWhichSave;

public class Commands {

    public static void showAccount(String nameService) {
    }
    public static void addAccount(AccWhichSave account) throws Exception {
        PassCipher passSave = new PassCipher();
        passSave.init();
    }
    public static void deleteAccount() {}
    public static void changeMasterKey() {}

}
