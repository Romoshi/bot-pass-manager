package edu.romoshi;

import edu.romoshi.DBTools.CRUDCommands;
import edu.romoshi.DBTools.CRUDUtils;
import edu.romoshi.crypto.PassCipher;
import edu.romoshi.userTools.AccWhichSave;

import java.util.List;
import java.util.Objects;

public class Commands {

    public static void useCommands(String command, AccWhichSave account) throws Exception {
        PassCipher pass = new PassCipher();
        pass.init();
        String passEncrypt = pass.encrypt(account.getPassword());
        String passDecrypt = PassCipher.decrypt(passEncrypt);

        switch (command) {
            case "Посмотреть аккаунты" -> {
                List<AccWhichSave> accounts = CRUDUtils.getAccounts(CRUDCommands.READ);
                //Сделать вывод сообщений через Telegram API
                accounts.forEach(s -> System.out.println("Название сервиса: " + s.getNameService() + "\n" +
                        "Логин: " + s.getLogin() + "\n" +
                        "Пароль: " + passDecrypt + "\n"));
            }
            case "Добавить новый аккаунт" -> {
                AccWhichSave cryptoAcc = new AccWhichSave(account.getNameService(), account.getLogin(), passEncrypt);
                CRUDUtils.saveAccount(cryptoAcc, CRUDCommands.CREATE);
            }
            case "Удалить аккаунт" -> CRUDUtils.deleteAccount("Yandex", CRUDCommands.DELETE);
            default -> System.err.println("Sorry, I don`t have this command");
        }
    }
}
