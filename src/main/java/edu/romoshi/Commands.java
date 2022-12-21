package edu.romoshi;

import edu.romoshi.userTools.AccWhichSave;

public class Commands {

    public static void useCommands(String command, AccWhichSave account) {
        switch(command) {
            case "Посмотреть аккаунт":
                //CRUDUtils.showAccounts();
                break;
            case "Добавить новый аккаунт":
                //CRUDUtils.saveAccount(account);
                break;
            case "Удалить аккаунт":
                //CRUDUtils.deleteAccount("Yandex");
                break;
            default:
                System.err.println("Sorry, I don`t have this command");
        }
    }
}
