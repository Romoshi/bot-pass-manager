package edu.romoshi.puller.bot;

import edu.romoshi.puller.bot.commands.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class Bot extends TelegramLongPollingBot {
    private static final Logger logger = LoggerFactory.getLogger(Bot.class);
    private static final String BOT_TOKEN = System.getenv("BOT_TOKEN");
    private static final String BOT_NAME = System.getenv("BOT_NAME");
    private final int AUTO_DELETE_MESSAGE_TIME = 3 * (int)Math.pow(10, 5); //5 minute
    private final Receiver receiver = new Receiver(new ConcurrentHashMap<>());
    private final Cache cache = new Cache(new ConcurrentHashMap<>(), new ArrayList<>());

    @Override
    public void onUpdateReceived(Update update) {
        try{
            if(update.hasMessage() && update.getMessage().hasText()) {
                Message inMess = update.getMessage();
                parseMessage(inMess);
            }
        } catch (Exception e) {
            logger.error("Update problems", e);
        }
    }

    private void parseMessage(Message message) {
        if(!Tables.isFlag()) {
            Tables.initTables();
        }

        cache.add(message);
        cache.autoDeleteCache(message);
        autoDeleteMessage(message);

        initCommands(cache.findPassFromCache(message));
        receiver.runCommand(message);

        if(!receiver.isFlag()) {
            DefaultCommand defaultCommand = new DefaultCommand();
            defaultCommand.execute(message);
        } else {
            receiver.setFlag(false);
        }
    }

    public void sendMsg(Message message, String s) {
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            sendMessage.setChatId(message.getChatId());
            sendMessage.setText(s);

            Message sentOutMessage = execute(sendMessage);
            autoDeleteMessage(sentOutMessage);
        } catch (TelegramApiException ex) {
            logger.error("Send Message trouble", ex);
        }
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
                    logger.error("Auto delete message", e);
                }
                time.cancel();
            }
        }, AUTO_DELETE_MESSAGE_TIME);
    }

    private void initCommands(boolean verifyKey) {
        receiver.addCommand(CommandStrings.START_COMMAND, new StartCommand());
        receiver.addCommand(CommandStrings.KEY_COMMAND, new KeyCommand(verifyKey));
        receiver.addCommand(CommandStrings.SHOW_COMMAND, new ShowCommand(verifyKey));
        receiver.addCommand(CommandStrings.SAVE_COMMAND, new SaveCommand(verifyKey));
        receiver.addCommand(CommandStrings.DELETE_COMMAND, new DeleteCommand(verifyKey));
        receiver.addCommand(CommandStrings.HELP_COMMAND, new HelpCommand());
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
