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
//        if () {
//            System.out.println("Давайте создадим пароль");
//            System.out.println("Введите пароль");
//        }

        System.out.println("Введите пароль для старта");
        Scanner scanner = new Scanner(System.in);
        MasterKey masterKey = new MasterKey(scanner.nextLine());

        String salt = MasterKeyUtils.generateSalt(512).get();
        String key = MasterKeyUtils.hashPassword(masterKey.toString(), salt).get();

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

//    public static AccWhichSave createAccount() {
//        //TODO: Заменить на телеграм апи
//        System.out.println("Введите название сервиса");
//        Scanner scannerNS = new Scanner(System.in);
//
//        System.out.println("Введите логин");
//        Scanner scannerL = new Scanner(System.in);
//
//        System.out.println("Введите пароль");
//        Scanner scannerP = new Scanner(System.in);
//
//        return new AccWhichSave(scannerNS.nextLine(), scannerL.nextLine(), scannerP.nextLine());
//    }
}
