package edu.romoshi.bot;

import at.favre.lib.crypto.bcrypt.BCrypt;
import edu.romoshi.Cache;
import edu.romoshi.bot.commands.CommandStrings;
import edu.romoshi.bot.commands.Handler;
import edu.romoshi.crypto.Decryption;
import edu.romoshi.crypto.Encryption;
import edu.romoshi.database.SQLUtils;
import edu.romoshi.user.Accounts;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PassManagerBot extends TelegramLongPollingBot {
    private static final String BOT_TOKEN = System.getenv("BOT_TOKEN");
    private static final String BOT_NAME = System.getenv("BOT_NAME");
    private PassManagerBot bot;
    private final int AUTO_DELETE_MESSAGE_TIME = 3 * (int)Math.pow(10, 5); //5 minute
    private final Cache cache = new Cache(new ConcurrentHashMap<>(), new ArrayList<>());

    @Override
    public void onUpdateReceived(Update update) {
        try{
            if(update.hasMessage() && update.getMessage().hasText())
            {
                Message inMess = update.getMessage();
                parseMessage(inMess);

                autoDeleteMessage(inMess);
                cache.autoDeleteCache(inMess);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseMessage(Message message) throws Exception {
        //Handler handler = new Handler(bot, message, new ConcurrentHashMap<>());
        String[] messageArray = message.getText().split(" ");
        cache.add(message);

        SQLUtils.createTableUser();
        SQLUtils.createTablePass();

        boolean verifyMK = cache.findPassFromCache(message);

        switch (messageArray[0]) {
            case CommandStrings.startCommand -> sendMsg(message, BotStrings.START_STRING);
            case CommandStrings.keyCommand -> {
                if(!verifyMK && SQLUtils.userExist(message)) {
                    if(messageArray.length == 2) {
                        String bcryptHashString = BCrypt.withDefaults().hashToString(12, messageArray[1].toCharArray());
                        SQLUtils.createUserMk(message, bcryptHashString);
                        sendMsg(message, BotStrings.KEY_STRING);
                        sendMsg(message, BotStrings.AFTER_KEY_STRING);
                    } else {
                        sendMsg(message, BotStrings.MISTAKE_MESSAGE);
                    }
                } else if (!messageArray[1].isEmpty()){
                    sendMsg(message, BotStrings.KEY_EXIST);
                }
            }
            case CommandStrings.showCommand -> {
                if (verifyMK) {
                    if(messageArray.length == 1) {
                        List<Accounts> accounts = SQLUtils.getAccounts(message);
                        for (var account : accounts) {
                            sendMsg(message, account.getServices(message));
                        }
                    } else {
                        sendMsg(message, BotStrings.MISTAKE_MESSAGE);
                    }
                }else {
                    sendMsg(message, BotStrings.START_STRING);
                }
            }
            case CommandStrings.saveCommand -> {
                if (verifyMK) {
                    if(messageArray.length == 4) {
                        Encryption en = new Encryption();
                        Accounts acc = new Accounts(messageArray[1], messageArray[2],
                                en.encrypt(messageArray[3], message.getChatId().toString()));
                        SQLUtils.saveAccount(acc, message);
                        sendMsg(message, BotStrings.SAVE_STRING);
                    } else {
                        sendMsg(message, BotStrings.MISTAKE_MESSAGE);
                    }
                } else {
                    sendMsg(message, BotStrings.START_STRING);
                }
            }
            case CommandStrings.deleteCommand -> {
                if (verifyMK) {
                    if(messageArray.length == 2) {
                        SQLUtils.deleteAccount(messageArray[1], message);
                        sendMsg(message, BotStrings.DELETE_STRING);
                    } else {
                        sendMsg(message, BotStrings.MISTAKE_MESSAGE);
                    }
                }else {
                    sendMsg(message, BotStrings.START_STRING);
                }
            }
            case CommandStrings.helpCommand -> {
                if(messageArray.length == 1) {
                    sendMsg(message, BotStrings.HELP_STRING);
                } else {
                    sendMsg(message, BotStrings.MISTAKE_MESSAGE);
                }
            }
            default -> sendMsg(message, BotStrings.DEFAULT_STRING);
        }
    }

    public void sendMsg(Message message, String s) throws TelegramApiException {
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
        }, AUTO_DELETE_MESSAGE_TIME);
    }

    private void createMapCommands() {}

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
