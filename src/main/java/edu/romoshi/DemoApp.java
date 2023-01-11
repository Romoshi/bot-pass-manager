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
        Scanner scanner = new Scanner(System.in);
        MasterKey masterKey = new MasterKey(scanner.nextLine());

        String salt = MasterKeyUtils.generateSalt(512).get();
        String key = MasterKeyUtils.hashPassword(masterKey.toString(), salt).get();

        String nameService = "Yandex";
        String login = "irina@yandex.ru";
        String password = "qwerty";

        AccWhichSave yandex = new AccWhichSave(nameService, login, password);

        if (MasterKeyUtils.verifyPassword("123", key, salt)) {
            CRUDUtils.createTable(CRUDCommands.CREATE_TABLE);
            Commands.useCommands("Посмотреть аккаунты", yandex);
        } else {
            System.err.println("Password is incorrect");
        }
    }
}
