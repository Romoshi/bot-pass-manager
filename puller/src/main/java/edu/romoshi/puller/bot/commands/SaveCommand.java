package edu.romoshi.puller.bot.commands;

import edu.romoshi.puller.bot.MessageStrings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

import static edu.romoshi.puller.Main.bot;

public class SaveCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(SaveCommand.class);
    private final boolean verifyKey;

    public SaveCommand(boolean verifyKey) {
        this.verifyKey = verifyKey;
    }
    @Override
    public void execute(Message message) {
        try {
            String[] messageArray = message.getText().split(" ");
            if (verifyKey) {
                if(messageArray.length == 4) {
                    Accounts account = new Accounts(messageArray[1], messageArray[2], messageArray[3]);
                    account.addAccount(message.getChatId().intValue());
                    bot.sendMsg(message, MessageStrings.SAVE_STRING);
                } else {
                    bot.sendMsg(message, MessageStrings.MISTAKE_MESSAGE);
                }
            } else {
                bot.sendMsg(message, MessageStrings.MISTAKE_MESSAGE);
            }
        } catch (Exception ex) {
            logger.error("Save command", ex);
        }
    }
}