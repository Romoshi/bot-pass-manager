package edu.romoshi;

import edu.romoshi.DBTools.CRUDCommands;
import edu.romoshi.DBTools.CRUDUtils;
import edu.romoshi.crypto.MasterKeyUtils;
import edu.romoshi.crypto.PassCipher;
import edu.romoshi.userTools.AccWhichSave;
import se.simbio.encryption.Encryption;

public class DemoApp {

    public static void main(String[] args) throws Exception {
        String userPass = "12345";
        String salt = MasterKeyUtils.generateSalt(512).get();
        String key = MasterKeyUtils.hashPassword(userPass, salt).get();

        String nameService = "Yandex";
        String login = "irina@yandex.ru";
        String password = "qwerty";

        //Препроцессинг пароля
        Encryption encryption = Encryption.getDefault(key, salt, new byte[16]);
        String passwordPreEncrypt = encryption.encryptOrNull(password);
        AccWhichSave yandex = new AccWhichSave(nameService, login, PassCipher.encrypt(passwordPreEncrypt));

        if (MasterKeyUtils.verifyPassword("12345", key, salt)) {
            CRUDUtils.createTable(CRUDCommands.CREATE_TABLE);
            Commands.useCommands("Добавить новый аккаунт", yandex);
        } else {
            System.err.println("Password is incorrect");
        }
    }
}
