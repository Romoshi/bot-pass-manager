package edu.romoshi.puller.bot.commands;

import edu.romoshi.puller.bot.MessageStrings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

import static edu.romoshi.puller.Main.bot;

public class HelpCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(HelpCommand.class);

    @Override
    public void execute(Message message) {
        try {
            bot.sendMsg(message, MessageStrings.HELP_STRING);
        } catch (Exception ex) {
            logger.error("Help command", ex);
        }
    }
}
