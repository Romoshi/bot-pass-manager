package edu.romoshi.handler.parsing.commands;

import edu.romoshi.polling.bot.BotStrings;
import edu.romoshi.handler.jdbc.accounts.Accounts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;


public class DeleteCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(DeleteCommand.class);
    private final boolean verifyKey;

    public DeleteCommand(boolean verifyKey) {
        this.verifyKey = verifyKey;
    }
    @Override
    public void execute(Message message) {
        String[] messageArray = message.getText().split(" ");
        try {
            if (verifyKey) {
                if(messageArray.length == 2) {
                    Accounts.deleteAccount(messageArray[1], message.getChatId().intValue());
                    Main.bot.sendMsg(message, BotStrings.DELETE_STRING);
                } else {
                    Main.bot.sendMsg(message, BotStrings.MISTAKE_MESSAGE);
                }
            }else {
                Main.bot.sendMsg(message, BotStrings.START_STRING);
            }
        } catch (Exception ex) {
            logger.error("Delete command", ex);
        }
    }
}
