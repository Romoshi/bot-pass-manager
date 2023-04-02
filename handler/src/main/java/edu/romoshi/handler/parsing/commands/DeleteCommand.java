package edu.romoshi.handler.parsing.commands;

import edu.romoshi.handler.jdbc.accounts.Accounts;
import edu.romoshi.handler.parsing.Handler;
import edu.romoshi.handler.parsing.MessageStrings;
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
                    Handler.hadlerQueue.add(MessageStrings.DELETE_STRING);
                } else {
                    Handler.hadlerQueue.add(MessageStrings.MISTAKE_MESSAGE);
                }
            }else {
                Handler.hadlerQueue.add(MessageStrings.START_STRING);
            }
        } catch (Exception ex) {
            logger.error("Delete command", ex);
        }
    }
}
