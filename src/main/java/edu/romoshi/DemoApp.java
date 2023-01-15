package edu.romoshi;

import edu.romoshi.DBTools.CRUDUtils;
import edu.romoshi.crypto.MasterKeyUtils;
import edu.romoshi.crypto.PassCipher;
import edu.romoshi.userTools.AccWhichSave;

import java.util.List;

public class DemoApp {

    public static void main(String[] args) throws Exception {
        String masterKey = "123";
        String salt = MasterKeyUtils.generateSalt(512).get();
        String key = MasterKeyUtils.hashPassword(masterKey, salt).get();

        AccWhichSave acc = new AccWhichSave("oasis1", "iam@gmail.com","1234");

        if (MasterKeyUtils.verifyPassword("123", key, salt)) {
            CRUDUtils.createTable();

            switch ("Посмотреть аккаунты") {
                case "Посмотреть аккаунты" -> {
                    List<AccWhichSave> accounts = CRUDUtils.getAccounts();

                    //TODO: Сделать вывод сообщений через Telegram API
                    accounts.forEach(account -> System.out.println(account.getServiceInfo()));
                }
                case "Добавить новый аккаунт" -> CRUDUtils.saveAccount(acc);
                case "Удалить аккаунт" -> CRUDUtils.deleteAccount("Yandex");
                default -> System.err.println("Sorry, I don`t have this command");
            }
        } else {
            System.err.println("Password is incorrect");
        }
    }
}
