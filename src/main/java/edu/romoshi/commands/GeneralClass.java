package edu.romoshi.commands;

import edu.romoshi.userTools.AccWhichSave;

public class GeneralClass {

    public static void useCommands(String command, AccWhichSave account) throws Exception {
        switch(command) {
            case "Посмотреть аккаунты":
                Commands.showAccounts();
                break;
            case "Добавить новый аккаунт":
                Commands.addAccount(account);
                break;
            case "Изменить аккаунт":
                Commands.changeAccount();
                break;
            case "Удалить аккаунт":
                Commands.deleteAccount();
                break;
            case "Забыл пароль от менеджера":
                Commands.changeMasterKey();
                break;
            default:
                System.err.println("Sorry, I don`t have this command");
        }
    }
}
