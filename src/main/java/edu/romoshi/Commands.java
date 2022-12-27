package edu.romoshi;

import edu.romoshi.DBTools.CRUDCommands;
import edu.romoshi.DBTools.CRUDUtils;
import edu.romoshi.userTools.AccWhichSave;

import java.util.List;

public class Commands {

    public static void useCommands(String command, AccWhichSave account) {
        switch(command) {
            case "Посмотреть аккаунт":
                List<AccWhichSave> accounts = CRUDUtils.getAccounts(CRUDCommands.READ);
                //Сделать вывод сообщений через Telegram API
                break;
            case "Добавить новый аккаунт":
                CRUDUtils.saveAccount(account, CRUDCommands.CREATE);
                break;
            case "Удалить аккаунт":
                CRUDUtils.deleteAccount("Yandex", CRUDCommands.DELETE);
                break;
            default:
                System.err.println("Sorry, I don`t have this command");
        }
    }
}
