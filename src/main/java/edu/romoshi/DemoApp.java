package edu.romoshi;

import edu.romoshi.DBTools.CRUDCommands;
import edu.romoshi.DBTools.CRUDUtils;
import edu.romoshi.crypto.MasterKeyUtils;
import edu.romoshi.crypto.PassCipher;
import edu.romoshi.userTools.AccWhichSave;
import edu.romoshi.userTools.MasterKey;
import se.simbio.encryption.Encryption;

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

        //Препроцессинг пароля
        Encryption encryption = Encryption.getDefault(key, salt, new byte[16]);
        String passwordPreEncrypt = encryption.encryptOrNull(password);

        PassCipher pass = new PassCipher();
        pass.init();
        AccWhichSave yandex = new AccWhichSave(nameService, login, pass.encrypt(passwordPreEncrypt));

        if (MasterKeyUtils.verifyPassword("123", key, salt)) {
            CRUDUtils.createTable(CRUDCommands.CREATE_TABLE);
            Commands.useCommands("Добавить новый аккаунт", yandex);
        } else {
            System.err.println("Password is incorrect");
        }
    }
}
