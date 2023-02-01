package edu.romoshi.bot;

import edu.romoshi.crypto.Decryption;
import edu.romoshi.crypto.Encryption;
import edu.romoshi.database.SQLUtils;
import edu.romoshi.user.Account;
import edu.romoshi.user.MasterKey;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static edu.romoshi.database.SQLUtils.userExist;

public class PassManagerBot extends TelegramLongPollingBot {
    private final String BOT_TOKEN = System.getenv("BOT_TOKEN");
    private final String BOT_NAME = System.getenv("BOT_NAME");

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
        MasterKey masterKey = new MasterKey(message.getChatId().toString());
        String[] messageArray = message.getText().split(" ");

        switch (messageArray[0]) {
            case BotStrings.START_COMMAND -> {
                SQLUtils.createTableUser();
                if(userExist(message)) SQLUtils.createUser(message);
                SQLUtils.createTablePass();

                sendMsg(message, BotStrings.START_STRING);
            }
            case BotStrings.SHOW_COMMAND -> {
                List<Account> accounts = SQLUtils.getAccounts();

                for (var account : accounts) {
                    Decryption de = new Decryption();
                    String answer = "Название сервиса: " + account.getNameService() + "\n" +
                            "Логин: " + account.getLogin() + "\n" +
                            "Пароль: " + de.decrypt(account.getPassword(), masterKey.getPassword());
                    sendMsg(message, answer);
                }
            }
            case BotStrings.SAVE_COMMAND -> {
                Encryption en = new Encryption();
                Account acc = new Account(messageArray[1], messageArray[2],
                                                    en.encrypt(messageArray[3], masterKey.getPassword()));
                SQLUtils.saveAccount(acc, message);
                sendMsg(message, "Аккаунт добавлен!");
            }
            case BotStrings.DELETE_COMMAND -> {
                SQLUtils.deleteAccount(messageArray[1]);
                sendMsg(message, "Аккаунт удалён!");
            }
            case BotStrings.HELP_COMMAND -> sendMsg(message, BotStrings.START_STRING);
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
