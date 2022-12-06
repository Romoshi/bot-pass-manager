package edu.romoshi;

import edu.romoshi.crypto.MasterKeyUtils;
import edu.romoshi.crypto.PassCipher;
import edu.romoshi.userTools.AccWhichSave;

public class DemoApp {

    public static void main(String[] args) throws Exception{

        //*** ПРОВЕРКА НА ПРАВИЛЬНОСТЬ ВВЕДЕНИЯ МАСТЕРА КЛЮЧА ***
        String salt = MasterKeyUtils.generateSalt(512).get();

        String originPass = "12345";

        String key = MasterKeyUtils.hashPassword(originPass, salt).get();

        if (MasterKeyUtils.verifyPassword("12345", key, salt)) {
            switch("Какая-то команда") {
                case "Добавить новый аккаунт":
                    // code block
                    break;
                case "Посмотреть аккаунт":
                    // code block
                    break;
                case "Изменить аккаунт":
                    // code block
                    break;
                case "Удалить аккаунт":
                    // code block
                    break;
                case "Забыл пароль от менеджера":
                    // code block
                    break;
                default:
                    System.err.println("Такой команды не существует");
            }
        } else {
            System.err.println("Password is incorrect");
        }

        //*** СИММЕТРИЧНОЕ ШИФРОВАНИЕ ПАРОЛЕЙ (которые хранятся в менеджере) ***
        AccWhichSave yandex = new AccWhichSave("Yandex", "irina@yandex.ru", "qwerty");

        String pass = yandex.getPassword();

        PassCipher passSave = new PassCipher();
        passSave.init();

        String encryptedPass = passSave.encrypt(pass);

        System.err.println("Original pass: " + pass);
        String decryptedPass = passSave.decrypt(encryptedPass);

        System.err.println("Encrypted pass: " + encryptedPass);
        System.err.println("Decrypted pass: " + decryptedPass);
    }
}
