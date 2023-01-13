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
        AccWhichSave acc1 = new AccWhichSave("oasis2", "iam@gmail.com","12345");

        AccWhichSave[] accs = {acc, acc1};

        for (var s : accs) {
            System.out.println(s.getPassword());
            System.out.println(s.getDecPass());
        }




//        if (MasterKeyUtils.verifyPassword("123", key, salt)) {
//            CRUDUtils.createTable();
//
//            switch ("Посмотреть аккаунты") {
//                case "Посмотреть аккаунты" -> {
//                    List<AccWhichSave> accounts = CRUDUtils.getAccounts();
//
//                    //TODO: Сделать вывод сообщений через Telegram API
//                    for (var account : accounts) {
//                        try {
//                            System.out.println("Название сервиса: " + account.getNameService() + "\n" +
//                                    "Логин: " + account.getLogin() + "\n" +
//                                    "Пароль: " + account.getDecPass() + "\n");
//                        } catch (Exception e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                }
//                case "Добавить новый аккаунт" -> CRUDUtils.saveAccount(acc);
//                case "Удалить аккаунт" -> CRUDUtils.deleteAccount("Yandex");
//                default -> System.err.println("Sorry, I don`t have this command");
//            }
//        } else {
//            System.err.println("Password is incorrect");
//        }
    }
}
