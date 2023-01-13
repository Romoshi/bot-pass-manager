package edu.romoshi;

import edu.romoshi.DBTools.CRUDCommands;
import edu.romoshi.DBTools.CRUDUtils;
import edu.romoshi.crypto.MasterKeyUtils;
import edu.romoshi.crypto.PassCipher;
import edu.romoshi.userTools.AccWhichSave;
import edu.romoshi.userTools.MasterKey;
import se.simbio.encryption.Encryption;

import java.util.List;
import java.util.Scanner;

public class DemoApp {

    public static void main(String[] args) throws Exception {
        String masterKey = "123";
        String salt = MasterKeyUtils.generateSalt(512).get();
        String key = MasterKeyUtils.hashPassword(masterKey, salt).get();

        PassCipher pass = new PassCipher();
        pass.init();
        String passExample = "12345";
        String passEncrypt = pass.encrypt(passExample);

        AccWhichSave account = new AccWhichSave("Yandex", "iam@yandex.ru", passEncrypt);

        if (MasterKeyUtils.verifyPassword("123", key, salt)) {
            CRUDUtils.createTable();
            Commands.useCommands("Посмотреть аккаунты", account);
        } else {
            System.err.println("Password is incorrect");
        }
    }
}
