package edu.romoshi;

import edu.romoshi.DBTools.CRUDUtils;
import edu.romoshi.userTools.AccWhichSave;

public class Commands {

    public static void useCommands(String command, AccWhichSave account) {
        switch(command) {
            case "Посмотреть аккаунты":
                CRUDUtils.showAccount("Yandex");
                break;
            case "Добавить новый аккаунт":
                CRUDUtils.saveAccount(account);
                break;
            case "Удалить аккаунт":
                CRUDUtils.deleteAccount("Google");
                break;
            case "Забыл пароль от менеджера":
                changeMasterKey();
                break;
            default:
                System.err.println("Sorry, I don`t have this command");
        }
    }

    public static void changeMasterKey() {

    }
}
