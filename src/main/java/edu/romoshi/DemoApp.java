package edu.romoshi;

import edu.romoshi.DBTools.SQLUtils;
import edu.romoshi.crypto.MasterKeyUtils;
import edu.romoshi.crypto.PassCipher;
import edu.romoshi.userTools.AccWhichSave;

import javax.crypto.SecretKey;
import java.util.List;

public class DemoApp {
    public static void main(String[] args) throws Exception {
        String masterKey = "123";
        String salt = MasterKeyUtils.generateSalt(512).get();
        String key = MasterKeyUtils.hashPassword(masterKey, salt).get();


        String nameService = "oasis1";
        String login = "iam@gmail.com";
        String password = "qwerty";

        PassCipher passCipher = new PassCipher();
        PassCipher.init();

        String passEncrypt = passCipher.encrypt(password);
        AccWhichSave acc = new AccWhichSave(nameService, login, passEncrypt);

//        if (MasterKeyUtils.verifyPassword("123", key, salt)) {
//            SQLUtils.createTable();
//
//            switch ("show") {
//                case "show" -> {
//                    List<AccWhichSave> accounts = SQLUtils.getAccounts();
//
//                    accounts.forEach(account -> {
//                        try {
//                            System.out.println(PassCipher.decrypt(sks, account.getPassword()));
//                        } catch (Exception e) {
//                            throw new RuntimeException(e);
//                        }
//                    });
//                }
//                case "add" -> SQLUtils.saveAccount(acc);
//                case "del" -> SQLUtils.deleteAccount("Yandex");
//                default -> System.err.println("Sorry, I don`t have this command");
//            }
//        } else {
//            System.err.println("Password is incorrect");
//        }
    }
}
