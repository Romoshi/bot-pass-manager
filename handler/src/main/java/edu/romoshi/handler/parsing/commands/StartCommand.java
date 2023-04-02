package edu.romoshi.handler.parsing.commands;

import edu.romoshi.polling.bot.BotStrings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

public class StartCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(StartCommand.class);

    @Override
    public void execute(Message message) {
        try {
            Main.bot.sendMsg(message, BotStrings.START_STRING);
        } catch (Exception ex) {
            logger.error("Start command", ex);
        }
    }
}
