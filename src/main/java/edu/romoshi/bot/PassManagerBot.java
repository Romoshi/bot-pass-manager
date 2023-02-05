package edu.romoshi.bot;

import at.favre.lib.crypto.bcrypt.BCrypt;
import edu.romoshi.crypto.Decryption;
import edu.romoshi.crypto.Encryption;
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


import static edu.romoshi.database.SQLUtils.userExist;

public class PassManagerBot extends TelegramLongPollingBot {
    private final String BOT_TOKEN = System.getenv("BOT_TOKEN");
    private final String BOT_NAME = System.getenv("BOT_NAME");
    Map<Integer, List<String>> cache = new HashMap<>();
    List<String> messages = new ArrayList<>();

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
        cache.put(message.getChatId().intValue(), messages);
        messages.add(message.getText());

        SQLUtils.createTableUser();
        SQLUtils.createTablePass();

        boolean verifyMK = findPassFromCache(cache, message);

        switch (messageArray[0]) {
            case BotStrings.START_COMMAND -> {
                sendMsg(message, BotStrings.START_STRING);
            }
            case BotStrings.MASTER_KEY_COMMAND -> {
                if(!verifyMK) {
                    if(userExist(message)) {
                        String bcryptHashString = BCrypt.withDefaults().hashToString(12, messageArray[1].toCharArray());
                        SQLUtils.createUserMk(message, bcryptHashString);
                        sendMsg(message, "Пароль создан.");
                        sendMsg(message, "Введите /help");
                    }
                } else {
                    sendMsg(message, "Пароль уже существует.");
                }
            }
            case BotStrings.SHOW_COMMAND -> {
                if (verifyMK) {
                    List<Accounts> accounts = SQLUtils.getAccounts(message);

                    for (var account : accounts) {
                        Decryption de = new Decryption();
                        String answer = "Название сервиса: " + account.getNameService() + "\n" +
                                "Логин: " + account.getLogin() + "\n" +
                                "Пароль: " + de.decrypt(account.getPassword(), message.getChatId().toString());
                        sendMsg(message, answer);
                    }
                }else {
                    sendMsg(message, BotStrings.START_STRING);
                }
            }
            case BotStrings.SAVE_COMMAND -> {
                if (verifyMK) {
                    Encryption en = new Encryption();
                    Accounts acc = new Accounts(messageArray[1], messageArray[2],
                            en.encrypt(messageArray[3], message.getChatId().toString()));
                    SQLUtils.saveAccount(acc, message);
                    sendMsg(message, "Аккаунт добавлен!");
                } else {
                    sendMsg(message, BotStrings.START_STRING);
                }
            }
            case BotStrings.DELETE_COMMAND -> {
                if (verifyMK) {
                    SQLUtils.deleteAccount(messageArray[1], message);
                    sendMsg(message, "Аккаунт удалён!");
                }else {
                    sendMsg(message, BotStrings.START_STRING);
                }
            }
            case BotStrings.HELP_COMMAND -> sendMsg(message, BotStrings.HELP_STRING);
            default -> sendMsg(message, "Введите /help");
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

    private static boolean findPassFromCache(Map<Integer, List<String>> map, Message message) {
        if (SQLUtils.mkExist(message)) return false;

        for(Map.Entry<Integer, List<String>> entry : map.entrySet()) {
            if(entry.getKey() == message.getChatId().intValue()) {
                for (var item : entry.getValue()) { //TODO: ГОВОРИТ, ЧТО ОШИБКА ГДЕ-ТО ЗДЕСЬ
                    BCrypt.Result result = BCrypt.verifyer().verify(item.toCharArray(), SQLUtils.getMk(message));
                    if(result.verified) return true;
                }
            }
        }

        return false;
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
