package edu.romoshi.handler.parsing.commands;

import edu.romoshi.handler.parsing.Handler;
import edu.romoshi.handler.parsing.MessageStrings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

public class DefaultCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(DefaultCommand.class);
    @Override
    public void execute(Message message) {
        try {
            Handler.hadlerQueue.add(MessageStrings.DEFAULT_STRING);
        } catch (Exception ex) {
            logger.error("Def command", ex);
        }
    }
}
