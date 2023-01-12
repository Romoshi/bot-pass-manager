package edu.romoshi;

import edu.romoshi.DBTools.CRUDCommands;
import edu.romoshi.DBTools.CRUDUtils;
import edu.romoshi.crypto.PassCipher;
import edu.romoshi.userTools.AccWhichSave;

import java.util.List;

public class Commands {

    public static void useCommands(String command, AccWhichSave account) throws Exception {
        switch (command) {
            case "Посмотреть аккаунты" -> {
                List<AccWhichSave> accounts = CRUDUtils.getAccounts();
                //TODO: Сделать вывод сообщений через Telegram API
                accounts.forEach(s -> System.out.println("Название сервиса: " + s.getNameService() + "\n" +
                        "Логин: " + s.getLogin() + "\n" +
                        "Пароль: " + PassCipher.decrypt(s.getPassword()) + "\n"));
            }
            case "Добавить новый аккаунт" -> CRUDUtils.saveAccount(account);
            case "Удалить аккаунт" -> CRUDUtils.deleteAccount("Yandex");
            default -> System.err.println("Sorry, I don`t have this command");
        }
    }
}
