package edu.romoshi.bot;

import edu.romoshi.crypto.Decryption;
import edu.romoshi.crypto.Encryption;
import edu.romoshi.crypto.MasterKeyUtils;
import edu.romoshi.database.SQLUtils;
import edu.romoshi.user.Accounts;
import edu.romoshi.user.MasterKey;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;
import java.util.logging.Level;

import static edu.romoshi.database.SQLUtils.userExist;

public class PassManagerBot extends TelegramLongPollingBot {
    private final String BOT_TOKEN = System.getenv("BOT_TOKEN");
    private final String BOT_NAME = System.getenv("BOT_NAME");
    Map<String, List<String>> cache = new HashMap<>();
    List<String> messages = new ArrayList<>();
    MasterKey masterKey;
    String salt = MasterKeyUtils.generateSalt(512).get();



    @Override
    public void onUpdateReceived(Update update) {

        try{
            if(update.hasMessage() && update.getMessage().hasText())
            {
                Message inMess = update.getMessage();
                parseMessage(inMess);
                autoDeleteMessage(inMess);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseMessage(Message message) throws Exception {
        String[] messageArray = message.getText().split(" ");
        messages.add(message.getText());
        cache.put(message.getChatId().toString(), messages);

        switch (messageArray[0]) {
            case BotStrings.START_COMMAND -> {
                SQLUtils.createTableUser();
                SQLUtils.createTablePass();
                sendMsg(message, BotStrings.START_STRING_ONE);
            }
            case "mk" -> sendMsg(message, BotStrings.START_STRING_TWO);
            case BotStrings.MASTER_KEY_COMMAND -> {
                if(masterKey == null) {
                    if(userExist(message)) {
                        masterKey = new MasterKey(messageArray[1]);
                        String key = MasterKeyUtils.hashPassword(masterKey.getPassword(), salt).get();
                        SQLUtils.createUserMk(message, key);
                        sendMsg(message, "Пароль создан.");
                    }
                } else {
                    sendMsg(message, "Пароль уже существует.");
                }
            }
            case BotStrings.SHOW_COMMAND -> {
                if (masterKey != null) {
                    List<Accounts> accounts = SQLUtils.getAccounts(message);

                    for (var account : accounts) {
                        Decryption de = new Decryption();
                        String answer = "Название сервиса: " + account.getNameService() + "\n" +
                                "Логин: " + account.getLogin() + "\n" +
                                "Пароль: " + de.decrypt(account.getPassword(), masterKey.getPassword());
                        sendMsg(message, answer);
                    }
                }else {
                    sendMsg(message, BotStrings.START_STRING_ONE);
                }
            }
            case BotStrings.SAVE_COMMAND -> {
                if (masterKey != null) {
                    Encryption en = new Encryption();
                    Accounts acc = new Accounts(messageArray[1], messageArray[2],
                            en.encrypt(messageArray[3], masterKey.getPassword()));
                    SQLUtils.saveAccount(acc, message);
                    sendMsg(message, "Аккаунт добавлен!");
                } else {
                    sendMsg(message, BotStrings.START_STRING_ONE);
                }
            }
            case BotStrings.DELETE_COMMAND -> {
                if (masterKey != null) {
                    SQLUtils.deleteAccount(messageArray[1], message);
                    sendMsg(message, "Аккаунт удалён!");
                }else {
                    sendMsg(message, BotStrings.START_STRING_ONE);
                }
            }
            case BotStrings.HELP_COMMAND -> sendMsg(message, BotStrings.HELP_STRING);
            default -> sendMsg(message, "Извините, но такой команды нет.");
        }
    }

    private void sendMsg(Message message, String s) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(s);

        Message sentOutMessage = execute(sendMessage);
        autoDeleteMessage(sentOutMessage);
    }

    private void autoDeleteMessage(Message message) {
        final Timer time = new Timer();
        DeleteMessage deleteMessage = new DeleteMessage(message.getChatId().toString(), message.getMessageId());
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    execute(deleteMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                time.cancel();
            }
        }, 300000);

    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
