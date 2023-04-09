package edu.romoshi.puller.bot.commands;

import edu.romoshi.puller.bot.MessageStrings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

import static edu.romoshi.puller.bot.Bot.bot;


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
                    //Accounts.deleteAccount(messageArray[1], message.getChatId().intValue());
                    bot.sendMsg(message, MessageStrings.DELETE_STRING);
                } else {
                    bot.sendMsg(message, MessageStrings.MISTAKE_MESSAGE);
                }
            }else {
                bot.sendMsg(message, MessageStrings.START_STRING);
            }
        } catch (Exception ex) {
            logger.error("Delete command", ex);
        }
    }
}
