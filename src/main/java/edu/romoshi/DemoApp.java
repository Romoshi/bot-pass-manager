package edu.romoshi;

import edu.romoshi.bot.Bot;
import edu.romoshi.database.SQLUtils;
import edu.romoshi.crypto.Decryption;
import edu.romoshi.crypto.Encryption;
import edu.romoshi.crypto.MasterKeyUtils;
import edu.romoshi.user.AccWhichSave;
import edu.romoshi.user.MasterKey;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import java.util.List;

public class DemoApp {
    public static void main(String[] args) throws Exception {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        //App
        MasterKey masterKey = new MasterKey("123");
        String salt = MasterKeyUtils.generateSalt(512).get();
        String key = MasterKeyUtils.hashPassword(masterKey.getPassword(), salt).get();

        if (MasterKeyUtils.verifyPassword("123", key, salt)) {
            SQLUtils.createTable();

            switch ("show") {
                case "show" -> {
                    List<AccWhichSave> accounts = SQLUtils.getAccounts();

                    accounts.forEach(account -> {
                        try {
                            Decryption de = new Decryption();
                            System.out.println("Название сервиса: " + account.getNameService() + "\n" +
                                                "Логин: " + account.getLogin() + "\n" +
                                                "Пароль: " + de.decrypt(account.getPassword(), masterKey.getPassword()) + "\n");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
                case "add" -> {
                    String nameService = "riki";
                    String login = "riki@gmail.com";
                    String password = "qwerty";

                    Encryption en = new Encryption();
                    AccWhichSave acc = new AccWhichSave(nameService, login, en.encrypt(password, masterKey.getPassword()));
                    SQLUtils.saveAccount(acc);
                }
                case "del" -> SQLUtils.deleteAccount("riki");
                default -> System.err.println("Sorry, I don`t know this command");
            }
        } else {
            System.err.println("Password is incorrect");
        }
    }
}
