package edu.romoshi.bot;

import edu.romoshi.Cache;
import edu.romoshi.Main;
import edu.romoshi.bot.commands.*;

import edu.romoshi.jdbc.Tables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PassManagerBot extends TelegramLongPollingBot {
    private static final Logger logger = LoggerFactory.getLogger(PassManagerBot.class);
    private static final String BOT_TOKEN = System.getenv("BOT_TOKEN");
    private static final String BOT_NAME = System.getenv("BOT_NAME");
    private final int AUTO_DELETE_MESSAGE_TIME = 3 * (int)Math.pow(10, 5); //5 minute
    private final Handler handler = new Handler(new ConcurrentHashMap<>());
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
            logger.error("Update problems", e);
        }
    }

    private void parseMessage(Message message) {
        cache.add(message);

        Tables.initTables();
        initCommands(message);
        handler.runCommand(message);

        if(!handler.isFlag()) {
            DefaultCommand defaultCommand = new DefaultCommand();
            defaultCommand.execute(message);
        } else {
            handler.setFlag(false);
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
                    logger.error("Auto delete message", e);
                }
                time.cancel();
            }
        }, AUTO_DELETE_MESSAGE_TIME);
    }

    private void initCommands(Message message) {
        boolean verifyKey = cache.findPassFromCache(message);

        handler.addCommand(CommandStrings.START_COMMAND, new StartCommand());
        handler.addCommand(CommandStrings.KEY_COMMAND, new KeyCommand(verifyKey));
        handler.addCommand(CommandStrings.SHOW_COMMAND, new ShowCommand(verifyKey));
        handler.addCommand(CommandStrings.SAVE_COMMAND, new SaveCommand(verifyKey));
        handler.addCommand(CommandStrings.DELETE_COMMAND, new DeleteCommand(verifyKey));
        handler.addCommand(CommandStrings.HELP_COMMAND, new HelpCommand());
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
