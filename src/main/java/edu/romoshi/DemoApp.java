package edu.romoshi;

import edu.romoshi.DBTools.SQLUtils;
import edu.romoshi.crypto.Decryption;
import edu.romoshi.crypto.Encryption;
import edu.romoshi.crypto.MasterKeyUtils;
import edu.romoshi.userTools.AccWhichSave;
import edu.romoshi.userTools.MasterKey;

import java.util.List;

public class DemoApp {
    public static void main(String[] args) throws Exception {
        MasterKey masterKey = new MasterKey("123");
        String salt = MasterKeyUtils.generateSalt(512).get();
        String key = MasterKeyUtils.hashPassword(masterKey.getPassword(), salt).get();


        String nameService = "riki";
        String login = "riki@gmail.com";
        String password = "dfgh";

        Encryption en = new Encryption();
        AccWhichSave acc = new AccWhichSave(nameService, login, en.encrypt(password, masterKey.getPassword()));

        if (MasterKeyUtils.verifyPassword("123", key, salt)) {
            SQLUtils.createTable();

            switch ("show") {
                case "show" -> {
                    List<AccWhichSave> accounts = SQLUtils.getAccounts();

                    accounts.forEach(account -> {
                        try {
                            Decryption de = new Decryption();
                            System.out.println("Название сервиса: " + account.getNameService() + "\n" +
                                                "Логин: " + account.getLogin() + "\n" +
                                                "Пароль: " + de.decrypt(account.getPassword(), masterKey.getPassword()) + "\n");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
                case "add" -> SQLUtils.saveAccount(acc);
                case "del" -> SQLUtils.deleteAccount("Yandex");
                default -> System.err.println("Sorry, I don`t have this command");
            }
        } else {
            System.err.println("Password is incorrect");
        }
    }
}
