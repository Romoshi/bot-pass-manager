package edu.romoshi.service;

import edu.romoshi.bot.Bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Timer;
import java.util.TimerTask;


public class MessageSender implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(MessageSender.class);
    private final int SENDER_SLEEP_TIME = 1000;
    private final int AUTO_DELETE_MESSAGE_TIME = 3 * (int)Math.pow(10, 5); //5 minute
    private final Bot bot;

    public MessageSender(Bot bot) {
        this.bot = bot;
    }
    @Override
    public void run() {
        try {
            while(true) {
                for(Object object = bot.sendQueue.poll(); object != null; object = bot.sendQueue.poll()) {
                    logger.debug("Get new msg to send " + object);
                    send(object);
                }
                try {
                    Thread.sleep(SENDER_SLEEP_TIME);
                } catch (InterruptedException ex) {
                    logger.error("Take interrupt while operate msg list", ex);
                }
            }
        } catch (Exception ex) {
            logger.error("run problems", ex);
        }
    }

    private void send(Object object) {
        try {
            BotApiMethod<Message> message = (BotApiMethod<Message>) object;
            Message msg = bot.execute(message);
            autoDeleteMessage(msg);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    private void autoDeleteMessage(Message message) {
        final Timer time = new Timer();
        DeleteMessage deleteMessage = new DeleteMessage(message.getChatId().toString(), message.getMessageId());
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    bot.execute(deleteMessage);
                } catch (TelegramApiException e) {
                    logger.error("Auto delete message", e);
                }
                time.cancel();
            }
        }, AUTO_DELETE_MESSAGE_TIME);
    }
}
