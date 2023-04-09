package edu.romoshi.puller;

import edu.romoshi.puller.bot.Bot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import static edu.romoshi.puller.bot.Bot.bot;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
            logger.info("Bot ON");
        } catch (TelegramApiException e) {
            logger.error("Problems with bot register", e);
        }
    }
}