package edu.romoshi.handler.parsing.commands;

import edu.romoshi.polling.bot.BotStrings;
import edu.romoshi.handler.crypto.Encryption;
import edu.romoshi.handler.jdbc.accounts.Accounts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

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
                    Encryption en = new Encryption();
                    Accounts account = new Accounts(messageArray[1], messageArray[2],
                            en.encrypt(messageArray[3], message.getChatId().toString()));
                    account.addAccount(message.getChatId().intValue());
                    Main.bot.sendMsg(message, BotStrings.SAVE_STRING);
                } else {
                    Main.bot.sendMsg(message, BotStrings.MISTAKE_MESSAGE);
                }
            } else {
                Main.bot.sendMsg(message, BotStrings.START_STRING);
            }
        } catch (Exception ex) {
            logger.error("Save command", ex);
        }
    }
}
