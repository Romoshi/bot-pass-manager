package edu.romoshi;

import edu.romoshi.crypto.MasterKeyUtils;
import edu.romoshi.userTools.AccWhichSave;

public class DemoApp {

    public static void main(String[] args) {
        String originPass = "12345";

        String salt = MasterKeyUtils.generateSalt(512).get();
        String key = MasterKeyUtils.hashPassword(originPass, salt).get();

        AccWhichSave yandex = new AccWhichSave("Yandex", "irina@yandex.ru", "qwerty");

        if (MasterKeyUtils.verifyPassword("12345", key, salt)) {
            Commands.useCommands("Добавить новый аккаунт", yandex);
        } else {
            System.err.println("Password is incorrect");
        }
    }
}
